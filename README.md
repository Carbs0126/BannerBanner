### BannerBanner

BannerBanner 是 https://github.com/youth5201314/banner 1.4.10 的优化版本，优化如下：

1. 更新至 androidx（本库适用于 androidx）

2. 规范代码命名方式，更改拼写错误，格式化代码，资源命名，id用法等

3. 删除 deprecated 代码

4. 更新 Glide、Fresco 库

5. 资源文件等命名添加 banner 前缀，避免与主工程的命名产生冲突



#### 看法

个人对 banner 库的一点建议和看法：

1. 优点：
总体来说代码实现了很多封装功能，满足了一般项目的 banner 需求；

2. 不足：
细微之处需要优化，如编码规范、拼写错误等；
部分功能用不到，增加了布局的复杂度，对于性能要求较高的UI，需要对Banner进行裁剪；
由于是继承了ViewPager，因此ViewPager中的不足并没有修复，如快速滑动并抬手时，ViewPager的切换速度并没有与手指速度一致
