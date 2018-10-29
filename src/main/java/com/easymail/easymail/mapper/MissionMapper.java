package com.easymail.easymail.mapper;


import com.easymail.easymail.entity.Mission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MissionMapper {
    void insertMission(Mission mission);
    List<Mission> getMissionsByTitle(@Param("title") String title);
    Mission getMissionByTitleAndName(@Param("title") String title,@Param("name") String name);
    void deleteMissionByTitleAndName(@Param("title") String title,@Param("name") String name);
    void insertMissions(List<Mission> missions);
    List<String> getAllMissionTitles();
}
