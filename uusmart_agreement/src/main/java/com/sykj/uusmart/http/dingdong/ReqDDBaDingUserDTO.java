/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.dingdong;
import com.sykj.uusmart.Constants;
import com.sykj.uusmart.http.baidu.BDHeaderDTO;
import com.sykj.uusmart.utils.RegularExpressionUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Auto-generated: 2018-07-06 16:51:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ReqDDBaDingUserDTO {


    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=4, max =255, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "386740421@qq.com", required =true, value="邮箱,L(4~255)")
//    @Pattern(regexp= RegularExpressionUtils.REGEX_EMAIL , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String account;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @Size(min=4, max =255, message= Constants.systemError.PARAM_VALUE_LENGTH)
    @ApiModelProperty(example = "123456", required =true, value="密码,L(4~255)")
//    @Pattern(regexp= RegularExpressionUtils.REGEX_EMAIL , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String password;

    @NotNull( message = Constants.systemError.PARAM_MISS)
    @ApiModelProperty(example = "HWej8eg7ybD7ggC56v/btG7DROibZdxJo82nmvwo7mwSwlPw9gna5XFyJtaymFwqm5/nUPz9sG/ItURIzbeX2JvW6bmn+SaPNYNlu3EXlnrZtt3surLkCT2voBZ7+tTfwiNTILvGdP7i5pYgxhGiS8UI/IkHcgeCQeNw9QzMBYC8zw/03XdK3oyHmXQj5weIB7tG1hj0ojc0xdKX+SJEa7EJNJ8z3OnzVQh1ro9EEQ0\\u003d", required =true, value="状态吗,L(*)")
//    @Pattern(regexp= RegularExpressionUtils.REGEX_EMAIL , message= Constants.systemError.PARAM_VALUE_INVALID )
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}