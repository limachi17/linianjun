namespace java com.moxian.common.proxy

service FileServerThrift {
   string generateQrCode(1:i64 userId, 2:i32 type)  
   string generateQrImageCode(1:i64 userId,2:i32 type)
}

 