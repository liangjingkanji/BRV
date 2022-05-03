package com.sample.flow_sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.drake.brv.listener.ItemDifferCallback
import com.drake.brv.utils.divider
import com.drake.brv.utils.setDifferModels
import com.drake.brv.utils.setup
import com.drake.brv.utils.staggered
import com.dylanc.viewbinding.nonreflection.binding
import com.sample.flow_sample.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val binding :FragmentHomeBinding by binding(FragmentHomeBinding::inflate)
    private val mViewModel: AnchorViewModel by viewModels()

    companion object {

        private const val TAG = "MainFragment"

        fun newInstance(type: String): MainFragment {
            val args = Bundle()
            args.putString("type", type)
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeToModel()
        initData()
        setListener()
    }

    private fun initView() {
        initRecyclerView()
    }
    private fun initData(){
        mViewModel.getHotAnchorList()
    }
    private fun setListener(){
        binding.refreshLayout.onRefresh {
            Log.d(TAG, "stateFlow: 下拉刷新")
            mViewModel.getHotAnchorList()
        }
        binding.refreshLayout.onLoadMore {
            Log.d(TAG, "stateFlow: 上拉加载更多")
            mViewModel.getHotAnchorList()
        }
    }

    private fun subscribeToModel() {
        lifecycleScope.launch {
            mViewModel.hotAnchorListStateFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    Log.d(TAG, "stateFlow: ${it.size}")
                    binding.refreshLayout.finish()
                    binding.recyclerView.setDifferModels(it)
                }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView
            .staggered(2)
            .divider {
                setDrawable(R.drawable.staggered_divider)
                startVisible = true
                endVisible = true
            }
            .setup {
                addType<FollowAnchor>(R.layout.item_follow_anchor)
                itemDifferCallback = object : ItemDifferCallback {
                    override fun getChangePayload(oldItem: Any, newItem: Any): Any? {
                        return true
                    }
                }
                onBind {

                }
            }
    }

}