package com.bamboo.boa.mapper;

import java.util.List;
import com.bamboo.boa.domain.BoaConferenceRoomsDO;
import com.bamboo.common.utils.sql.BaseMapperX;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 会议室信息Mapper接口
 * 
 * @author bamboo
 * @date 2024-03-07
 */
@Repository
public interface BoaConferenceRoomsMapper extends BaseMapperX<BoaConferenceRoomsDO>
{
    /**
     * 查询会议室信息
     * 
     * @param id 会议室信息主键
     * @return 会议室信息
     */
    public BoaConferenceRoomsDO selectBoaConferenceRoomsById(Long id);

    /**
     * 查询会议室信息列表
     * 
     * @param boaConferenceRooms 会议室信息
     * @return 会议室信息集合
     */
    public List<BoaConferenceRoomsDO> selectBoaConferenceRoomsList(BoaConferenceRoomsDO boaConferenceRooms);

    /**
     * 新增会议室信息
     * 
     * @param boaConferenceRooms 会议室信息
     * @return 结果
     */
    public int insertBoaConferenceRooms(BoaConferenceRoomsDO boaConferenceRooms);

    /**
     * 修改会议室信息
     * 
     * @param boaConferenceRooms 会议室信息
     * @return 结果
     */
    public int updateBoaConferenceRooms(BoaConferenceRoomsDO boaConferenceRooms);

    /**
     * 删除会议室信息
     * 
     * @param id 会议室信息主键
     * @return 结果
     */
    public int deleteBoaConferenceRoomsById(Long id);

    /**
     * 批量删除会议室信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBoaConferenceRoomsByIds(Long[] ids);
}
