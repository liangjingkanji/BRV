package com.sample.flow_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.drake.brv.PageRefreshLayout
import com.gyf.immersionbar.ktx.immersionBar
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(this).apply {
                //footer.setAccentColor(android.R.color.white) //设置强调颜色
                setPrimaryColor(0xfff6f9f9.toInt()) //设置主题颜色
            }
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(this) .apply {
                //footer.setAccentColor(android.R.color.white) //设置强调颜色
                setPrimaryColor(0xfff6f9f9.toInt()) //设置主题颜色
            }
        }
        PageRefreshLayout.startIndex=0
        immersionBar {
            statusBarColor(android.R.color.white)
            statusBarDarkFont(true)
        }

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragmentContainer,MainFragment.newInstance("0"),"main")
        }
    }
}