package com.drake.brv.sample.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drake.brv.BindingAdapter
import com.drake.brv.sample.R
import com.drake.brv.sample.constants.Api
import com.drake.brv.sample.databinding.DialogDouyinCommentBinding
import com.drake.brv.sample.model.DouyinCommentModel
import com.drake.brv.sample.model.DouyinCommentReplyModel
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineBottomSheetDialogFragment
import com.drake.engine.dialog.setMaxWidth
import com.drake.engine.utils.dp
import com.drake.net.Get
import com.drake.net.utils.scope
import com.drake.net.utils.scopeNetLife
import com.drake.tooltip.toast

class DouyinCommentDialog : EngineBottomSheetDialogFragment<DialogDouyinCommentBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_douyin_comment, container, false)
    }

    override fun initView() {
        binding.v = this
        behavior.peekHeight = 700.dp
        setMaxWidth()
        binding.rv.setup {
            addType<DouyinCommentModel.Data>(R.layout.item_douyin_comment_1)
            addType<DouyinCommentReplyModel.Data>(R.layout.item_douyin_comment_2)
            R.id.tvComment.onClick {
                toast("未实现回复功能")
            }
            R.id.llExpandComment.onClick {
                getModelOrNull<DouyinCommentModel.Data>()?.let {
                    expandReply()
                }
                getModelOrNull<DouyinCommentReplyModel.Data>()?.let {
                    expandMoreOrCollapseRepOrly(this, this@setup, it)
                }
            }
        }
    }

    override fun initData() {
        binding.page.onRefresh {
            scope {
                val res = Get<DouyinCommentModel>(Api.COMMENT).await()
                addData(res.list) {
                    itemCount < res.total
                }
            }
        }.showLoading()
    }

    /**
     * 展开评论回复
     */
    private fun BindingAdapter.BindingViewHolder.expandReply() {
        val model = getModel<DouyinCommentModel.Data>()
        if (!model.itemExpand) {
            if (model.comments.size >= model.commentCount) {
                expand()
            } else {
                model.loading = true
                scopeNetLife {
                    val data = Get<DouyinCommentReplyModel>(Api.COMMENT).await().list
                    model.addComment(data)
                    expand()
                }.finally {
                    model.loading = false
                }
            }
        } else {
            collapse()
            model.loading = false
        }
    }

    /**
     * 展开更多评论回复或者收起评论回复
     */
    private fun expandMoreOrCollapseRepOrly(
        vh: BindingAdapter.BindingViewHolder,
        adapter: BindingAdapter,
        replyModel: DouyinCommentReplyModel.Data
    ) {
        val parentPosition = vh.findParentPosition()
        val model = adapter.getModel<DouyinCommentModel.Data>(parentPosition)
        if (replyModel.isHasMore()) {
            val last = model.comments.last()
            last.loading = true
            scopeNetLife {
                val data = Get<DouyinCommentReplyModel>(Api.COMMENT).await().list
                model.addComment(data)
                adapter.addModels(data, index = vh.layoutPosition + 1)
            }.finally {
                last.loading = false
            }
        } else {
            model.itemExpand = false
            model.notifyChange()
            val replyComments = model.comments.toSet()
            adapter.mutable.removeAll(replyComments)
            adapter.notifyItemRangeRemoved(parentPosition + 1, replyComments.size)
        }
    }

    override fun onClick(v: View) {
        when (v) {
            binding.ivClose -> dismiss()
        }
    }
}