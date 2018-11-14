package com.easymail.easymail.mapper;

import com.easymail.easymail.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    Account findAccountByAddress(@Param("address") String address);
}
