列表动画只是实现列表Item出现的动画

## 动画类型
通过函数快速设置BRV自带的几种动画类型

```kotlin
fun setAnimation(animationType: AnimationType)
```

使用`AnimationType`枚举

| 枚举 | 动画描述 |
|-|-|
| ALPHA | 渐变 |
| SCALE | 缩放动 |
| SLIDE_BOTTOM | 底部滑入 |
| SLIDE_LEFT | 左侧滑入 |
| SLIDE_RIGHT | 右侧滑入 |


## 自定义列表动画

如果默认的动画无法满足你, 你可以参考自带的`ItemAnimation`子类动画来自定义你要的动画效果

```kotlin
fun setAnimation(itemAnimation: ItemAnimation)
```

这里以`AlphaItemAnimation`源码示例

```kotlin
class AlphaItemAnimation @JvmOverloads constructor(private val mFrom: Float = DEFAULT_ALPHA_FROM) : ItemAnimation {

    override fun onItemEnterAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f).setDuration(300).start() // 渐变动画
    }

    companion object {

        private val DEFAULT_ALPHA_FROM = 0f // 初始透明度
    }
}
```