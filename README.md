------
## 缓存问题：

> * #### 缓存击穿
> * #### 缓存失效（缓存雪崩）
> * #### 热点数据
> * #### 缓存穿透
##### 上述是我们在缓存应用中可能会出现的问题！

### 一、缓存击穿
#### 1、何谓缓存击穿？
   缓存击穿就是某一瞬间大量请求打进来，按理说应该是除了第一个请求会访问DB，
其他的请求都应该访问缓存，实际情况是大部分请求却直接执行了DB。
#### 2、缓存击穿危害
   在高并发环境中如果存在缓存击穿可能会击穿我们的DB如连接数过多，连接池满等，
最终的结果拖垮DB，造成线上故障。
#### 3、缓存击穿解决方案
   采用双重锁校验方案，因为单例模式中的双重锁检验是经常实现的，相信大家都比较
好理解。
