demo演示：

![](https://github.com/Sbingo/ViewPagerIndicator/raw/master/gif/ViewPagerIndicator.gif) 

### 库描述

 1. 配合ViewPager使用的圆点指示器，每滑到另一页并停止时，随机改变圆点颜色，内置了5种颜色.
 
 2. 如果页面没变，停止时颜色不会改变，圆点的颜色也可以自行设置.
 
### 使用方法

 - 在项目根 build.gradle 添加：
```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

 - 在要使用的模块中添加依赖：
```
compile 'com.github.Sbingo:ViewPagerIndicator:2.0.0'
```
 
 - 在布局中使用:
 
```
    <sbingo.com.mylibrary.ViewPagerIndicator
        android:id="@+id/vp_indicator"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_above="@id/radio_group"/>

```
 - 在代码中控制:
 ```   
        private boolean fromRadioGroup; 

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.item1:
                        if (viewPager.getCurrentItem() != 0) {
                            fromRadioGroup = true; //防止直接点击tab时，会一路调用"onPageScrolled"，造成绘制效果不好，目前只能这样控制……
                            viewPager.setCurrentItem(0);
                        }
                        break;
			  	......
			  	......
			  	......
                    default:
                }
            }
        })
        
        int[] colors = {Color.CYAN, Color.LTGRAY};
        vpIndicator.setColors(colors); //可以设置喜欢的颜色，但每次也是随机的：
        
         vpIndicator.setPageCount(4); //第一步：设置ViewPager的页数
         viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!fromRadioGroup) { 
                    vpIndicator.drawPoint(position,positionOffset); //第二步：滑动时绘制圆点
                }
            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        vpIndicator.setCurrentPageIndex(currentIndex); //第三步：停止时绘制圆点
                        fromRadioGroup = false;
                        break;
                    default:
                }
            }
        });
        
        

```


如果感觉不错，不妨顺手点个 Star 吧, 谢啦~

### 公众号
扫描下方二维码，关注我的公众号 

分享各种知识、干货，给自己多一扇看世界的窗
    
          as彬哥六六六
![as彬哥六六六](https://s2.ax1x.com/2019/03/22/A8dPfA.jpg)

