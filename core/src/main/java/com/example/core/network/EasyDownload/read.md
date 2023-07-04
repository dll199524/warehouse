
https://github.com/AriaLyy/Aria/tree/master

long taskId = Aria.download(this)
.load(DOWNLOAD_URL)     //读取下载地址
.setFilePath(DOWNLOAD_PATH) //设置文件保存的完整路径
.create();   //创建并启动下载


Aria.download(this)
.load(taskId)     //读取任务id
.stop();       // 停止任务
//.resume();    // 恢复任务