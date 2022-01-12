- BRV的分割线函数`divider`都是通过创建`DefaultDecoration`来实现的. 如果想访问更多功能请查看该类公开函数
- 假设自带的分割线功能不满足你的要求你可以继承`RecyclerView.ItemDecoration`实现

| 函数 | 描述 |
|-|-|
| onEnabled | 是否为当前Item启用分割线 |
| addType | 假设使用该函数则只有被添加的类型才会绘制分割线 |
| setColor | 设置分割线颜色 |
| setDrawable | 设置分割线图片 |
| setBackground | 设置分割线背景颜色 |
| setMargin | 设置分割线间距 |

其中有一个工具类可以计算出Item位于列表的边缘位置, 可以用于来设置间距, 即`Edge.computeEdge()`返回结果为`Edge`

```kotlin
data class Edge(
    var left: Boolean = false,
    var top: Boolean = false,
    var right: Boolean = false,
    var bottom: Boolean = false
)
```

left为true则表示指定position位于列表左侧, top为true则表示指定position位于列表顶部, 其他类推. 即可根据其结果来设置ItemView的间距