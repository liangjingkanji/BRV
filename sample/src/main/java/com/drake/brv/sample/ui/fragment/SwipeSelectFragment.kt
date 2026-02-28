package com.drake.brv.sample.ui.fragment

import android.view.View
import com.drake.brv.BindingAdapter
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentSwipeSelectBinding
import com.drake.brv.sample.model.SwipeSelectModel
import com.drake.brv.utils.addSwipeSelect
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment


class SwipeSelectFragment : EngineFragment<FragmentSwipeSelectBinding>(R.layout.fragment_swipe_select) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<SwipeSelectModel>(R.layout.item_swipe_select)

            // 长按进入编辑模式并选中当前条目
            onLongClick(R.id.item) {
                if (!toggleMode) {
                    toggle()
                    setChecked(layoutPosition, true)
                }
            }

            // 单击切换选中状态（编辑模式下）
            onFastClick(R.id.item) {
                if (!toggleMode) return@onFastClick
                checkedSwitch(layoutPosition)
            }

            // 选中状态变化回调
            onChecked { position, isChecked, _ ->
                val model = getModel<SwipeSelectModel>(position)
                model.checked = isChecked
                model.notifyChange()
                binding.tvCheckedCount.text = "已选择 ${checkedCount}/${modelCount}"
            }

            // 编辑模式切换回调
            onToggle { position, toggleMode, _ ->
                val model = getModel<SwipeSelectModel>(position)
                model.showCheckbox = toggleMode
                model.notifyChange()
                updateEditMode(this)
            }

        }.models = getData()

        // 添加滑动多选支持
        binding.rv.addSwipeSelect()

        initEditMode()
    }

    private fun initEditMode() {
        val adapter = binding.rv.bindingAdapter

        binding.tvManage.setOnClickListener {
            adapter.toggle()
        }

        binding.tvCancel.setOnClickListener {
            adapter.checkedAll(false)
        }

        binding.tvSelectAll.setOnClickListener {
            adapter.checkedAll()
        }

        binding.tvReverse.setOnClickListener {
            adapter.checkedReverse()
        }
    }

    private fun updateEditMode(adapter: BindingAdapter) {
        val toggleMode = adapter.toggleMode
        binding.tvManage.text = if (toggleMode) "取消" else "编辑"
        binding.llMenu.visibility = if (toggleMode) View.VISIBLE else View.GONE
        binding.tvCheckedCount.visibility = if (toggleMode) View.VISIBLE else View.GONE
        binding.tvCheckedCount.text = "已选择 0/${adapter.modelCount}"
        if (!toggleMode) adapter.checkedAll(false)
    }

    private fun getData(): List<SwipeSelectModel> {
        return List(20) { SwipeSelectModel() }
    }

    override fun initData() {}
}
