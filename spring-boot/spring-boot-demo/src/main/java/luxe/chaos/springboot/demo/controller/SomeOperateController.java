package luxe.chaos.springboot.demo.controller;

import luxe.chaos.springboot.demo.helper.OneLevelAsyncContext;
import luxe.chaos.springboot.demo.service.SomeOperateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/7/2021 3:53 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class SomeOperateController {


    private OneLevelAsyncContext oneLevelAsyncContext = new OneLevelAsyncContext();

    private SomeOperateService someOperateService;

    public SomeOperateController(SomeOperateService someOperateService) {
        this.someOperateService = someOperateService;
    }

    @GetMapping("/v1/info1")
    public void getInfo(HttpServletRequest request) {
        oneLevelAsyncContext.submitFuture(request, () -> this.someOperateService.getInfo() );
    }

    @GetMapping("/v1/info2")
    public String getInfo2() {
        return this.someOperateService.getInfo();
    }
    @GetMapping("/v1/ping")
    public String ping() {
        return  "Pong";
    }
}
