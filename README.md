传统的TabLayout，不管字数多少，下划线都是充满，如何使字体长度和下划线一样长？


![image](https://github.com/546554574/CustomTabLayout/blob/master/gif.gif)

属性：
  <attr name="titles" format="string" />
        <attr name="select_text_color" format="color" />
        <attr name="unselect_text_color" format="color" />
        <attr name="line_padding" format="integer" />
        
        titles:头部文字显示，中间以逗号（,）隔开，注意是英文输入法下的逗号
        select_text_color：选中的文字颜色
        unselect_text_color：非选中的文字颜色
        line_padding：文字和下划线之间的距离
        
        如果需要下划线的颜色可以自己在属性里面添加，不会的话可以Issues中写一下
