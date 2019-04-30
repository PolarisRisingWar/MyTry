# MyTry
This is my homework in the fourth semester in SWUFE.  
这个完全就是我拿来学习AS功能的，所以整个工程没有一个完全统一的功能。我大作业还没开始写，现在还不知道大作业应该写什么。  
C2FActivity的功能是输入摄氏度温度，转换为华氏度温度。  
newTeamActivity是个计分器界面，可以统计两支队伍的得分。同时还可以适配横屏。
rateActivity是汇率兑换功能的主界面。用户输入人民币数值，转换为欧元、韩元、欧元，汇率每日自动上传http://www.usd-cny.com/bankofchina.htm （中行）的汇率；用户可以跳转到changeRate界面自定义汇率；点击菜单上的水瓶图标跳转到列表界面RateListActivity，显示所有中行网页中显示的币种的汇率；点击菜单下的“使用摄氏度转换华氏度功能”跳转到C2FActivity界面；点击菜单下的“使用计分器功能”跳转到newTeamActivity。  
changeRate界面用于用户自定义汇率。  
RateListActivity是一个使用ListActivity父类的列表界面，显示所有从中行网页中获得的币种的汇率。
LoginActivity是一个登录界面。  
MainActivity没什么东西。  
MyListActivity是一个自己添加ListView组件而形成的列表界面。  
