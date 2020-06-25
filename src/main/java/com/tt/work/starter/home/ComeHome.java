package com.tt.work.starter.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author wenshilong
 * @date 2020/06/23
 */
@Controller
@RequestMapping("/comeHome")
public class ComeHome {

    @RequestMapping("/import")
    public String test(){
        return "import";
    }


    @RequestMapping("/seeTable")
    public String seeTable(){
        return "seeTable";
    }

}
