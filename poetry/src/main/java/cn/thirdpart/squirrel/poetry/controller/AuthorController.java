package cn.thirdpart.squirrel.poetry.controller;

import cn.thirdpart.squirrel.poetry.vo.AuthorVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class AuthorController {

    /**
     * 增加作者
     * @param authorVo
     * @return
     */
    @ApiOperation(value = "addAuthor", notes = "增加作者信息")
    @RequestMapping(value = "/author", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public String addAuthor(@RequestBody @ApiParam(name="author对象",value="传入json string 格式",required=true) AuthorVo authorVo){

        return null;
    }

}
