package com.easymail.easymail.mapper;


import com.easymail.easymail.entity.Mission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MissionMapper {
    void insertMission(@Param("mission") Mission mission,@Param("accountId")Integer accountId);
    List<Mission> getMissionsByTitle(@Param("title") String title,@Param("accountId") Integer accountId);
    Mission getMissionByTitleAndName(@Param("title") String title,@Param("name") String name,@Param("accountId") Integer accountId);
    void deleteMissionByTitleAndName(@Param("title") String title,@Param("name") String name,@Param("accountId") Integer accountId);
    void insertMissions(@Param("missions") List<Mission> missions,@Param("accountId") Integer accountId);
    List<String> getAllMissionTitles(@Param("accountId") Integer accountId);
    void deleteMissionByTitle(@Param("title")String title,@Param("accountId") Integer accountId);
}
