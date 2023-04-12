package com.drake.brv.sample.model

import androidx.databinding.BaseObservable
import com.drake.brv.reflect.copyType
import com.drake.statelayout.Status

@kotlinx.serialization.Serializable
data class HomeModel(
    var banner: List<Banner> = listOf(),
    var explore: List<Explore> = listOf(),
    var event: Event = Event(),
    var tabs: List<Tab> = listOf()
) {

    /** 获取列表有效数据 */
    fun getAvailableData(games: List<GameModel.Data>): MutableList<Any> {
        val data = mutableListOf<Any>()
        // 如果不使用copyType, 则会导致BRV无法区分List<Banner>和List<Explore>区别, Java泛型擦除问题
        // 或者定义不同类的List, 如BannerList<T>和ExploreList<T>也是可以区分的
        if (banner.isNotEmpty()) data.add(banner.copyType())
        if (explore.isNotEmpty()) data.add(explore.copyType())
        if (event.id != 0L) data.add(event)
        data.add("最新折扣")

        if (games.isEmpty()) {
            data.add(Status.EMPTY) // 如果你需要游戏列表为空时列表区域显示空缺省页
        } else {
            data.addAll(games) // 游戏列表
        }
        return data
    }

    @kotlinx.serialization.Serializable
    data class Banner(
        var id: Long = 0,
        var image: String = "",
    )

    @kotlinx.serialization.Serializable
    data class Explore(
        var id: Long = 0,
        var image: String = "",
    )

    @kotlinx.serialization.Serializable
    data class Event(
        var id: Long = 0,
        var image: String = "",
    )

    @kotlinx.serialization.Serializable
    data class Tab(
        var id: Long = 0,
        var title: String = "",
        var count: Int = 0
    ) : BaseObservable() {
        var checked = false
    }

    @kotlinx.serialization.Serializable
    class BannerList<T> : ArrayList<T>()

    @kotlinx.serialization.Serializable
    class ExploreList<T> : ArrayList<T>()
}