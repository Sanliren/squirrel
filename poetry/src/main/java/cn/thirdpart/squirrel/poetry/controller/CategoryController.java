package cn.thirdpart.squirrel.poetry.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 诗词接口
 */
@RestController
public class CategoryController {

    @RequestMapping(value = "/category", method = {RequestMethod.GET}, produces = "application/json; charset=utf-8")
    public String getCategory(){

        return null;
    }

}
