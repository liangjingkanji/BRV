package com.drake.brv.sample.ui.fragment

import android.view.View
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentDouyinCommentBinding
import com.drake.brv.sample.ui.dialog.DouyinCommentDialog
import com.drake.engine.base.EngineFragment

class DouyinCommentFragment : EngineFragment<FragmentDouyinCommentBinding>(R.layout.fragment_douyin_comment) {

    private val commentDialog = DouyinCommentDialog()

    override fun initView() {
        binding.v = this
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        showDialog()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnShowDialog -> showDialog()
        }
    }

    private fun showDialog() {
        if (commentDialog.dialog?.isShowing != true) {
            commentDialog.show(childFragmentManager, null)
        }
    }
}