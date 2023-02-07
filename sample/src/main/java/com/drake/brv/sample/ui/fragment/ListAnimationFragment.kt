package com.drake.brv.sample.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import com.drake.brv.BindingAdapter
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentListAnimationBinding
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import kotlin.random.Random

class ListAnimationFragment : EngineFragment<FragmentListAnimationBinding>(R.layout.fragment_list_animation) {

    private lateinit var adapter: BindingAdapter

    override fun initView() {
        setHasOptionsMenu(true)

        binding.tvReload.setOnClickListener {
            binding.rv.models = getData()
        }

        adapter = binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple_text)

            animationRepeat = binding.switchRepeat.isChecked
            setAnimation(AnimationType.ALPHA)

            onBind {
                findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
            }
        }
        adapter.models = getData()

        binding.switchRepeat.setOnCheckedChangeListener { _, b ->
            adapter.animationRepeat = b
        }
    }

    override fun initData() {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_anim, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.animTypeAlpha -> adapter.setAnimation(AnimationType.ALPHA)
            R.id.animTypeSCALE -> adapter.setAnimation(AnimationType.SCALE)
            R.id.animTypeSlideBottom -> adapter.setAnimation(AnimationType.SLIDE_BOTTOM)
            R.id.animTypeAlphaSlideRight -> adapter.setAnimation(AnimationType.SLIDE_RIGHT)
            R.id.animTypeAlphaSlideLeft -> adapter.setAnimation(AnimationType.SLIDE_LEFT)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData(): List<SimpleModel> {
        return mutableListOf<SimpleModel>().apply {
            for (i in 0..Random.nextInt(40, 120)) add(SimpleModel().apply {
                this.name += " ($i)"
            })
        }
    }
}