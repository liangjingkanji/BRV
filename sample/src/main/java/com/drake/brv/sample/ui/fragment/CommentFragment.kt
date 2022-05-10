package com.drake.brv.sample.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentCommentBinding
import com.drake.brv.sample.model.CommentModel
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class CommentFragment : EngineFragment<FragmentCommentBinding>(R.layout.fragment_comment) {

    /** 最好的Json序列化框架 https://github.com/Kotlin/kotlinx.serialization */
    private val json = Json {
        ignoreUnknownKeys = true
    }

    /** 模拟加载网络数据 */
    private val comments by lazy {
        json.decodeFromStream<List<CommentModel>>(resources.openRawResource(R.raw.compose))
    }

    override fun initView() {
        binding.rv.setup {
            addType<String>(R.layout.item_comment_title)
            addType<CommentModel>(R.layout.item_comment)
            onBind {
                if (itemViewType == R.layout.item_comment) {
                    val model = getModel<CommentModel>()
                    val rv = findView<RecyclerView>(R.id.rvNested)
                    rv.setup {
                        addType<CharSequence>(R.layout.item_nested_comment)
                        // 查看子评论
                        R.id.item.onClick {
                            toast("查看子评论")
                        }
                    }.models = model.comment()
                }
            }

            // 按热度
            R.id.tvFilter.onClick {
                sortWorks()
            }
            // 点击头像
            R.id.ivAvatar.onClick {
                toast("点击头像")
            }
            // 点赞
            R.id.tvUp.onClick {
                getModel<CommentModel>().like()
            }
            // 踩
            R.id.ivDown.onClick {
                getModel<CommentModel>().apply {
                    down = !down
                }.notifyChange()
            }
            // 分享
            R.id.ivShare.onClick {
                toast("分享")
            }
            // 输入评论
            R.id.ivInput.onClick {
                toast("输入评论")
            }
            // 更多
            R.id.ivMore.onClick {
                toast("更多")
            }
        }
    }

    override fun initData() {
        val models = mutableListOf<Any>()
        models.add("title") // 标题
        models.addAll(comments) // 评论列表
        binding.rv.models = models
    }

    /** 随机打乱排序 */
    private fun sortWorks() {
        binding.rv.models = mutableListOf<Any>().apply {
            add("title")
            addAll(comments.shuffled())
        }
    }
}