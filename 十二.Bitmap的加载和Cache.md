#《Android开发艺术探索》读书笔记
#第十二章 Bitmap的加载和Cache
##12.1 Bitmap的高效加载
	
1.先了解下如何加载一个Bitmap，Bitmap在Android中指的是一张图片，可以是jpg或者png等其他格式，BitmapFactory类提供了4个方法去加载一个Bitmap，decodeFile，decodeResource，decodeStream和decodeByteArray，分别用于支持从文件系统，资源，输入流以及字节数组中加载出一个Bitmap对象，其中decodeFile和decodeResource又间接调用了decodeStream方法，四个方法最终是在Android的底层实现的，对应着BitmapFactory的几个native方法；<br>
	2.怎么高效地加载Bitmap呢，核心思想是采用BitmapFactory.	Options来加载所需图片的尺寸，可以按照一定的采样率来加载缩小后的图片，能避免OOM,提高了Bitmap加载时的性能。<br>BitmapFactory提供的4类加载Bitmap的方法都支持BitmapFactory.	Options参数，通过他们就可以方便地对一个图片进行采样缩放；<br>
	3.通过BitmapFactory.	Options来缩放图片，主要用到了他的inSampleSize参数，即采样率。注意一个特殊情况，当采样率小于1时，作用相当于1即无缩放效果。获取采样率的流程如下：<br>
	(1)将BitmapFactory.Options的inJustDecodeBounds参数设置为true并加载图片；<br>
	(2)从BitmapFactory.Options中取出图片的原始宽高信息，对应于outWidth和outHeght参数；<br>
	(3)根据采样率的规则结合目标View的大小计算出采样率；<br>
	(4)将BitmapFactory.Options的inJustDecodeBounds参数设置为false并重新加载图片；<br>
	4.其他几个加载Bitmap的方法也是支持采样率的，只是decodeStream有点特殊；<br>
##12.2 Android中的缓存策略
	
缓存策略主要包括缓存的添加，获取和删除三类操作，为什么要删除呢，因为缓存也要占用空间，那么怎么判断新旧缓存，如何取舍呢==>LRU,采用LRU算法的缓存有两种：LruCache和DiskLruCache，完美结合可以实现一个实用的ImageLoader。<br>
	1.LruCache<br>
	是Android3.1提供的一个缓存类，通过v4包可以兼容到早期的版本。<br>
	是一个泛型类，内部采用LinkedHashMap以强引用的方式存储外界的缓存对象，提供了get和put方法完成缓存的获取和添加操作；<br>
	①强引用：直接的对象引用，new出来的就是；<br>
	②软引用：当一个对象只有软引用存在时，系统内存不足会被gc；<br>
	③弱引用：当一个对象只有弱引用存在时，此对象随时会被gc；<br>
	另外LruCache是线程安全的；<br>
	需要重写sizeOf方法，用于计算缓存大小的大小，一些特殊情况下还需要重写entryRemoved方法，LruCache移除旧缓存时会调用entryRemoved方法，可以在这个方法中完成资源回收的操作。<br>
	2.DiskLruCache<br>
	获得了Android官方文档的推荐，但是不属于AndroidSDK的一部分；<br>
	(1)DiskLruCache的创建<br>
	DiskLruCache并不能通过构造方法来创建，提供了open方法用于创建自身。open方法有4个参数，①第一个参数表示磁盘缓存在文件系统中的存储路径，可以选择SD卡上的缓存目录，具体是指/sdcard/Android/data/package_name/cache目录，应用卸载后，此目录会一并被删除。建议是：如果卸载后希望删除缓存文件，就选择SD卡上的缓存目录；<br>
	②第二个参数表示应用的版本号，一般设为1即可，当版本号发生改变时DiskLruCache会清空之前所有的缓存文件，而这个特性在实际开发中意义不大；<br>
	③第三个参数表示单个节点对应的数据的个数，一般设为1即可；<br>
	④第四个参数表示缓存的总大小，比如50M,缓存大小超过这个值的时候回清除掉一些；<br>
	(2)DiskLruCache的缓存添加<br>
	通过Editor完成的，Editor表示一个缓存对象的编辑对象；<br>
	首先要获取图片url所对应的key，然后根据key就可以通过edit()来获取Editor对象，如果这个对象正在被编辑，那么edit()就会返回null，即DiskLruCache不允许同时编辑一个缓存对象。之所以要把url转换成key，是因为图片的url中很可能有特殊字符，一般采用url的md5值作为key。<br>
	将图片的url转成key之后，就可以获取Editor对象了，对于这个key，如果不存在其他Editor对象，那么edit()就会返回一个新的Editor对象，通过他可以得到一个文件输出流，有了这个文件输出流，那么当网络下载图片时，图片就可以通过这个文件输出流写入到文件系统上；这个过程并没有真正将图片写入文件系统必须通过Editor的commit()来提交写入操作，如果出了异常，可以通过Editor的abort()来回退整个操作。<br>
	(3)DiskLruCache的缓存查找<br>
	也需要将url转为key，然后通过DiskLruCache的get方法得到一个Snapshot对象，再通过这个对象得到缓存的文件输入流，有了文件输入流，自然就可以得到Bitmap对象了。不建议直接加载原始图片。但是BitmapFactory.Options的这种方法对FileInputStream的缩放存在问题，因为FileInputStream是一种有序的文件流，而两次decodeStream的调用影响了文件流的位置属性，导致了第二次decodeStream得到的是null，为了解决这个问题，可以通过文件流得到他对应的文件描述符，再通过BitmapFactory.decodeFileDescription方法来加载一张缩放后的图片。<br>
	此外，DiskLruCache还提供了remove，delete等删除操作。<br>
	3.ImageLoader的实现<br>
	一个好的ImageLoader应该有以下的功能：<br>
	①图片的同步加载<br>②图片的异步加载<br>③图片压缩<br>④内存缓存<br>⑤磁盘缓存<br>⑥网络拉取<br>
	除此之外还要处理一些特殊情况，比如在ListView或者GridView，View的复用即是他的优点也是缺点<br>
	(1)图片压缩功能的实现<br>
	ImageResizer类<br>
	(2)内存缓存和磁盘缓存的实现(核心，也是ImageLoader的意义所在)<br>
	(3)同步加载和异步加载接口的设计<br>
##12.3 ImageLoader的使用