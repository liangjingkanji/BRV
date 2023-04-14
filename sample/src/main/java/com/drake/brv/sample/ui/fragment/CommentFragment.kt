package com.drake.brv.sample.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.sample.R
import com.drake.brv.sample.constants.Api
import com.drake.brv.sample.databinding.FragmentCommentBinding
import com.drake.brv.sample.model.CommentModel
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.net.Get
import com.drake.net.utils.scope
import com.drake.tooltip.toast

class CommentFragment : EngineFragment<FragmentCommentBinding>(R.layout.fragment_comment) {

    override fun initView() {
        binding.rv.setup {
            addType<String>(R.layout.item_comment_header)
            addType<CommentModel.Data>(R.layout.item_comment)
            onBind {
                if (itemViewType == R.layout.item_comment) {
                    val model = getModel<CommentModel.Data>()
                    val rv = findView<RecyclerView>(R.id.rvNested)
                    rv.setup {
                        addType<CharSequence>(R.layout.item_comment_2) // 查看子评论
                        R.id.item.onClick {
                            toast("查看子评论")
                        }
                    }.models = model.comments()
                }
            }

            // 按热度
            R.id.tvFilter.onClick {
                changeFilter()
            } // 点击头像
            R.id.ivAvatar.onClick {
                toast("点击头像")
            } // 点赞
            R.id.tvUp.onClick {
                getModel<CommentModel.Data>().favoriteAction()
            } // 踩
            R.id.ivDown.onClick {
                getModel<CommentModel.Data>().apply {
                    annoying = !annoying
                }.notifyChange()
            } // 分享
            R.id.ivShare.onClick {
                toast("分享")
            } // 输入评论
            R.id.ivInput.onClick {
                toast("输入评论")
            } // 更多
            R.id.ivMore.onClick {
                toast("更多")
            }
        }
    }

    override fun initData() {
        binding.page.onRefresh {
            scope {
                val res = Get<CommentModel>(Api.COMMENT).await()
                val models = mutableListOf<Any>().apply {
                    if (index == 1) add("标题")
                    addAll(res.list.shuffled())
                }
                addData(models) {
                    itemCount < res.total
                }
            }
        }.showLoading()
    }

    private fun changeFilter() {
        binding.page.refresh() // 静默更新
    }
}