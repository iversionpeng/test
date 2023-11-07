# test

问题描述：
正在用的对象里面管理着堆外内存，后面没有引入这个对象了，发生多次gc 后，这个对象没了，导致堆外内存被回收，程序直接崩溃

jvm参数：
-XX:CICompilerCount=4 -XX:InitialHeapSize=536870912 -XX:MaxHeapSize=629145600 -XX:MaxNewSize=209715200 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=178782208 -XX:OldSize=358088704 -XX:+PrintGC -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseFastUnorderedTimeStamps -XX:+UseParallelGC 


OriginLightCheck 88行 创建MatVector，在进入138行后，对应的183行，会不停的gc，导致MatVector被回收了；
创建完创建MatVector，就放到了自定义监控对象里

第256次循环
1290993: 22:47:49.222  MatVector地址hash:  0X600003d74240
![image](https://github.com/iversionpeng/test/assets/44367180/75f94976-46cf-4b55-ad9e-7b13b04aa168)


1291842: 22:47:49.261自定义监控线程监控到对象被清理
![image](https://github.com/iversionpeng/test/assets/44367180/012dfd0e-10ab-44d8-99d8-080fc689875b)


1292105: 22:47:49.263 - 22:47:49.267 javaCV清理线程执行 
![image](https://github.com/iversionpeng/test/assets/44367180/ed47dd9a-1144-417e-8120-9924e8ee8063)



22:47:49.267 发现cnt内存值被修改
![image](https://github.com/iversionpeng/test/assets/44367180/a8ed8fbd-f120-4d26-8b11-cdbd7cb15b26)



1293350: 22:47:49.317 报错
![image](https://github.com/iversionpeng/test/assets/44367180/65a1f6e9-0569-41bf-9ba2-ba7c45548c5d)





