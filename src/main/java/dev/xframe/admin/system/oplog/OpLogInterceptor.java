package dev.xframe.admin.system.oplog;

import dev.xframe.admin.system.OpUser;
import dev.xframe.http.service.Request;
import dev.xframe.http.service.Response;
import dev.xframe.http.service.config.HttpInterceptor;
import dev.xframe.inject.Bean;
import dev.xframe.inject.Inject;
import dev.xframe.utils.XLogger;
import dev.xframe.utils.XStrings;
import io.netty.handler.codec.http.HttpMethod;

@Bean
public class OpLogInterceptor implements HttpInterceptor {
    
    @Inject
    private OpLogRepo logRepo;
    
    @Override
    public void after(Request req, Response resp) {//succ ops
        String user = OpUser.clear();
        if(user != null) {
            if(!req.method().equals(HttpMethod.GET)) {
                String params = XStrings.newStringUtf8(req.content());
                String path = req.trimmedPath();
                
                XLogger.info("User[{}] req[{}] with[{}]...", user, path, params);
                
                logRepo.add(new OpLog(user, path, params));
            }
        }
    }
    
}