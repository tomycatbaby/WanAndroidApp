# Android

---

### 1、Android 项目中 assets 目录和 res 目录有什么区别
assets下可以新建子目录，res下的子目录不能再新建子目录
assets也会随着apk一起打包，但是不会进行编译，res目录下的文件都会进行编译后使用R.的方式就能使用
assets使用是需要用AssetManager取出输入流使用的
### 2、如何结束一个正在运行的线程
使用interrupted()方法打断线程的运行，不建议使用已遗弃的stop方法，因为stop的本质上是不安全的，用 Thread.stop 来终止线程将释放它已经锁定的所有监视器（作为沿堆栈向上传播的未检查 ThreadDeath 异常的一个自然后果）。如果以前受这些监视器保护的任何对象都处于一种不一致的状态，则损坏的对象将对其他线程可见，这有可能导致任意的行为。stop的许多使用都应由只修改某些变量以指示目标线程应该停止运行的代码来取代。目标线程应定期检查该变量，并且如果该变量指示它要停止运行，则从其运行方法依次返回。如果目标线程等待很长时间（例如基于一个条件变量），则应使用 interrupt 方法来中断该等待
### 3、如何获取某个 View 的大小并动态改变 View 的大小
LayoutParams动态改变view的大小
### 4、String、StringBuilder、StringBuffer 区别
运行速度：StringBuilder>StringBuffer>String
线程安全：String，StringBuffer
非线程安全：StringBuilder
String是字符串常量，每次的修改会导致对象的不断创建和回收，所以它最慢
StringBuffer由于其中很多方法都是带有synchronize关键字的，所以它的速度比StringBuilder慢
String是不可变类，所以String是线程安全的
缓冲区区别：StringBuffer每次toString会直接使用toStringCache来构造一个字符串
StringBuilder则每次需要复制一次字符数组，然后构造一个字符串
### 5、泛型擦除
修饰成员变量等类结构相关的泛型不会被擦除
Java的容器类泛型只对编译器有效，作为编码时的检验，编译为字节码后会自动将泛型去掉。
### 6、ArrayMap、HashMap、SparseArray 原理
HashMap是Java的数据结构，ArrayMap和SparseArray是Android特有的类
HashMap是数组加链表的存储value的结构，利用key的hashcode与容量-1相与去算出位于数组哪个位置，hash冲突后就加到链表中，java8之前都是链表，java8之后链表长度大于7后自动转换成红黑树（O(lgn)），优点是查找速度更快，缺点是会增加大量内存使用。
SparseArray很简单粗暴，利用Int作为key。它保存两个数组，一个时Int类型的key数组，一个是Object类型的value数组，加入数据是先用二分查找去查找是否在key数组已经存在有这个相同的key，没有就新增，有就返回对应value的在数组中下标，然后替换，这个数据类的缺点是增删改查都会使用二分查找，不适用存储大量数据
ArrayMap和SparseArray类似，区别是它使用hash值存储在key数组中的，SparseArray是直接存储int在key数组中，缺点也是查找效率没HashMap高，它主要是提高内存效率。
### 7、Fragment 添加方式，add、replace、show 区别
add():向Activity加入一个片段，这个片段在activity容器中有他自己的视图。
hide():隐藏Fragment，已经添加到父容器中的Fragment有关，隐藏Fragment的View。
show():显示被隐藏的Fragment，这仅仅对已经添加到activity中的Fragment有关,显示Fragment的View。
detach():Fragment的视图被销毁，但是它的状态没有被销毁，还是被FragmentMenager管理。
attach():Fragment的view重新加载到UI视图中，并显示，也就是执行onCreateView()→onActivityCreate()→onStart()→onResume()。
replace():就相当于执行remove(Fragment)→add(int, Fragment, String)这两个方法
### 8、volatile 关键字作用
禁止指令重排序，轻量级的synchronized，保重了共享变量的“可见性”，可见性的意思是一个线程修改变量的值是另一个线程是能读到这个值
当一个变量被 volatile 修饰时，任何线程对它的写操作都会立即刷新到主内存中，并且会强制让缓存了该变量的线程中的数据清空，必须从主内存重新读取最新数据
### 9、Binder 机制
Binder 是一种跨进程的通信方式，它也是一个类，实现了IBinder接口，从framework层面讲，它是ServiceManager链接各个service的桥梁，它也是应用层bindService之后给客户端提供调用服务端的服务或数据，服务包括普通服务和AIDL
### 10、HTTP 版本之间区别
一、HTTP1.0和HTTP1.1的区别
HTTP1.0最早在网页中使用是在1996年，那个时候只是使用一些较为简单的网页上和网络请求上，而HTTP1.1则在1999年才开始广泛应用于现在的各大浏览器网络请求中，同时HTTP1.1也是当前使用最为广泛的HTTP协议。 主要区别主要体现在：

1、缓存处理。在HTTP1.0中主要使用header里的If-Modified-Since,Expires来做为缓存判断的标准，HTTP1.1则引入了更多的缓存控制策略例如Entity tag，If-Unmodified-Since, If-Match, If-None-Match等更多可供选择的缓存头来控制缓存策略。

2、带宽优化及网络连接的使用。HTTP1.0中，存在一些浪费带宽的现象，例如客户端只是需要某个对象的一部分，而服务器却将整个对象送过来了，并且不支持断点续传功能，HTTP1.1则在请求头引入了range头域，它允许只请求资源的某个部分，即返回码是206（Partial Content），这样就方便了开发者自由的选择以便于充分利用带宽和连接。

3、错误通知的管理。在HTTP1.1中新增了24个错误状态响应码，如409（Conflict）表示请求的资源与资源的当前状态发生冲突；410（Gone）表示服务器上的某个资源被永久性的删除。

4、Host头处理。在HTTP1.0中认为每台服务器都绑定一个唯一的IP地址，因此，请求消息中的URL并没有传递主机名（hostname）。但随着虚拟主机技术的发展，在一台物理服务器上可以存在多个虚拟主机（Multi-homed Web Servers），并且它们共享一个IP地址。HTTP1.1的请求消息和响应消息都应支持Host头域，且请求消息中如果没有Host头域会报告一个错误（400 Bad Request）。

5、长连接。HTTP 1.1支持长连接（PersistentConnection）和请求的流水线（Pipelining）处理，在一个TCP连接上可以传送多个HTTP请求和响应，减少了建立和关闭连接的消耗和延迟，在HTTP1.1中默认开启Connection： keep-alive，一定程度上弥补了HTTP1.0每次请求都要创建连接的缺点
二、HTTP2.0和HTTP1.X相比的新特性
1、新的二进制格式（Binary Format）。HTTP1.x的解析是基于文本。基于文本协议的格式解析存在天然缺陷，文本的表现形式有多样性，要做到健壮性考虑的场景必然很多，二进制则不同，只认0和1的组合。基于这种考虑HTTP2.0的协议解析决定采用二进制格式，实现方便且健壮。

2、多路复用（MultiPlexing）。即连接共享，即每一个request都是是用作连接共享机制的。一个request对应一个id，这样一个连接上可以有多个request，每个连接的request可以随机的混杂在一起，接收方可以根据request的 id将request再归属到各自不同的服务端请求里面。

3、header压缩。如HTTP1.x的header带有大量信息，而且每次都要重复发送，HTTP2.0使用encoder来减少需要传输的header大小，通讯双方各自cache一份header fields表，既避免了重复header的传输，又减小了需要传输的大小。

4、服务端推送（server push）。同SPDY一样，HTTP2.0也具有server push功能。

三、HTTPS与HTTP的一些区别
1、HTTPS协议需要到CA申请证书，一般免费证书很少，需要交费。
2、HTTP协议运行在TCP之上，所有传输的内容都是明文，HTTPS运行在SSL/TLS之上，SSL/TLS运行在TCP之上，所有传输的内容都经过加密的。
3、HTTP和HTTPS使用的是完全不同的连接方式，用的端口也不一样，前者是80，后者是443。
4、HTTPS可以有效的防止运营商劫持，解决了防劫持的一个大问题。

### 11、invalidate()、requestLayout() 区别：
1. requeLayout() : 控件会重新执行 onMesure() onLayout() ,比如 ScrollView中有LinearLaout ，LinearLayout里面有纵向排列的ImageView和TextView,那么假如ImageView的长宽发生了变化，而要立即在手机上显示这个变化的话，就可调用 imageView.requestLayout();这样的话ScrollView 会重新执行onMesure()这个方法会确定控件的大小然后在确定出自己的宽高，最后在执行onLayout()，这个方法是对所有的子控件进行定位的。他只调用measure()和layout()过程，不会调用draw()。
2.invalidate() :是自定义View 的时候，重新执行onDraw()方法，当view只在内容和可见度方面发生变化时调用。
3 layout()：对控件进行重新定位执行onLayout()这个方法，比如要做一个可回弹的ScrollView，思路就是随着手势的滑动子控件滑动，那么我们可以将ScrollView的子控件调用layout（l,t,r,b）这个方法就行了。
### 12、ArrayList 怎么实现线程安全
使用synchronized关键字在调用这个实例的地方加锁或者使用Collections.synchronizedList()方法将一个ArrayList变成一个增删改查都加锁的类的实例，还有就是使用CopyOnWriteArrayList，这个类是线程安全的
### 13、ConcurrentHashMap为什么效率比Hashtable高？
jdk1.7下
Segment（分段锁）类，它继承自ReentrantLock。
ConcurrentHashMap使用分段锁技术，将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问，能够实现真正的并发访问

- 坏处
这一种结构的带来的副作用是Hash的过程要比普通的HashMap要长
- 好处
写操作的时候可以只对元素所在的Segment进行加锁即可，不会影响到其他的Segment，这样，在最理想的情况下，ConcurrentHashMap可以最高同时支持Segment数量大小的写操作（刚好这些写操作都非常平均地分布在所有的Segment上）。

jdk1.8
JDK8中彻底放弃了Segment转而采用的是Node，其设计思想也不再是JDK1.7中的分段锁思想。
Node：保存key，value及key的hash值的数据结构。其中value和next都用volatile修饰，保证并发的可见性。
CAS+Synchronized保证线程安全
### 14、List、Map、Set 哪个能存null，哪个能重复？
List是数组结构，插入数据时它并不对是否是null进行判断，所以它不仅允许null，还可以有多个null
Set的底层实现就是用Map的哈希值去维持唯一，所以说它是允许有null的，但是只能有一个
Map允许key和value都允许null，Hashtable都不允许null，他们都不允许重复key
### 15、Handler epoll 机制
管道是Linux系统中的一种进程间通信机制，简单来说，管道就是一个文件，在管道的两端，分别是两个打开文件文件描述符，这两个打开文件描述符都是对应同一个文件，其中一个是用来读的，别一个是用来写的，一般的使用方式就是，一个线程通过读文件描述符中来读管道的内容，当管道没有内容时，这个线程就会进入等待状态，而另外一个线程通过写文件描述符来向管道中写入内容，写入内容的时候，如果另一端正有线程正在等待管道中的内容，那么这个线程就会被唤醒。这个等待和唤醒的操作是如何进行的呢，这就要借助Linux系统中的epoll机制了。 Linux系统中的epoll机制为处理大批量句柄而作了改进的poll，是Linux下多路复用IO接口select/poll的增强版本，它能显著减少程序在大量并发连接中只有少量活跃的情况下的系统CPU利用率。但是这里我们其实只需要监控的IO接口只有mWakeReadPipeFd一个，即前面我们所创建的管道的读端，为什么还需要用到epoll呢？有点用牛刀来杀鸡的味道。其实不然，这个Looper类是非常强大的，它除了监控内部所创建的管道接口之外，还提供了addFd接口供外界面调用，外界可以通过这个接口把自己想要监控的IO事件一并加入到这个Looper对象中去，当所有这些被监控的IO接口上面有事件发生时，就会唤醒相应的线程来处理，不过这里我们只关心刚才所创建的管道的IO事件的发生
epoll_wait  监控的超时时间为timeoutMillis