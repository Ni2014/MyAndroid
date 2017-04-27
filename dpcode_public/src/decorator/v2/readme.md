1 对比BufferedFileStream  和 BufferedNetworkStream 等类
    buffered部分的逻辑是需要被复用的
    提取出来BufferedStream 至于具体是FileStream 还是 NetworkStream 都可以用Stream表示
    先把继承转组合