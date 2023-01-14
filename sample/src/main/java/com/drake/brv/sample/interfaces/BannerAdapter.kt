package com.drake.brv.sample.interfaces

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drake.brv.sample.model.HomeModel
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(data: MutableList<HomeModel.Banner>? = null) : BannerAdapter<HomeModel.Banner, HomeBannerAdapter.BannerViewHolder>(data) {

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder {
        val img = ImageView(parent.context)
        img.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        img.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(img)
    }

    override fun onBindView(
        holder: BannerViewHolder,
        data: HomeModel.Banner,
        position: Int,
        size: Int
    ) {
        val img = holder.itemView as ImageView
        Glide.with(img)
            .load(data.image)
            .into(img)
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}