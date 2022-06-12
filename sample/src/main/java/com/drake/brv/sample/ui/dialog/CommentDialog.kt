package com.drake.brv.sample.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drake.brv.BindingAdapter
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.DialogCommentBinding
import com.drake.brv.sample.model.CommentDialogModel
import com.drake.brv.sample.model.CommentReplyModel
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineBottomSheetDialogFragment
import com.drake.engine.dialog.setMaxWidth
import com.drake.engine.utils.dp
import com.drake.net.utils.scopeNetLife
import com.drake.tooltip.toast
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class CommentDialog : EngineBottomSheetDialogFragment<DialogCommentBinding>() {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_comment, container, false)
    }

    override fun initView() {
        binding.v = this
        behavior.peekHeight = 700.dp
        setMaxWidth()
        binding.rv.setup {
            addType<CommentDialogModel>(R.layout.item_dialog_comment)
            addType<CommentReplyModel>(R.layout.item_dialog_comment_reply)
            R.id.tvComment.onClick {
                toast("未实现回复功能")
            }
            R.id.llExpandComment.onClick {
                getModelOrNull<CommentDialogModel>()?.let {
                    expandComments()
                }
                getModelOrNull<CommentReplyModel>()?.let {
                    expandMoreComments(this, this@setup, it)
                }
            }
        }
    }

    override fun initData() {
        binding.rv.models = getComments<List<CommentDialogModel>>()
    }

    /**
     * 展开评论
     */
    private fun BindingAdapter.BindingViewHolder.expandComments() {
        val model = getModel<CommentDialogModel>()
        if (!model.itemExpand) {
            if (model.comments.size >= model.commentCount) {
                expand()
            } else {
                model.loading = true
                scopeNetLife {
                    delay(1000) // 模拟网络延迟
                    val data = getComments<List<CommentReplyModel>>()
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
     * 展开更多评论
     * 收起评论
     */
    private fun expandMoreComments(
        vh: BindingAdapter.BindingViewHolder,
        adapter: BindingAdapter,
        model: CommentReplyModel
    ) {
        val parentPosition = vh.findParentPosition()
        val parentModel = adapter.getModel<CommentDialogModel>(parentPosition)
        if (model.isHasMore()) {
            val data = getComments<List<CommentReplyModel>>()
            parentModel.addComment(data)
            adapter.addModels(data, index = vh.layoutPosition + 1)
        } else {
            parentModel.itemExpand = false
            val replyComments = parentModel.comments
            adapter.mutable.removeAll(replyComments)
            adapter.notifyItemRangeRemoved(parentPosition + 1, replyComments.size)
        }
    }

    /** 模拟加载网络数据 */
    private inline fun <reified T> getComments(): T {
        return json.decodeFromStream(resources.openRawResource(R.raw.comment))
    }

    override fun onClick(v: View) {
        when (v) {
            binding.ivClose -> dismiss()
        }
    }
}