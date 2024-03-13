package com.bamboo.boa.service;

import java.util.List;
import com.bamboo.boa.domain.BoaConferenceRoomsDO;
import com.bamboo.boa.domain.vo.BoaConferenceRoomsVO;
import com.bamboo.boa.domain.vo.ImgUrl;
import com.bamboo.common.core.domain.AjaxResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 会议室信息Service接口
 * 
 * @author bamboo
 * @date 2024-03-07
 */
public interface IBoaConferenceRoomsService extends IService<BoaConferenceRoomsDO>
{
    /**
     * 查询会议室信息
     *
     * @param id 会议室信息主键
     * @return 会议室信息
     */
    public BoaConferenceRoomsVO selectBoaConferenceRoomsById(Long id);

    /**
     * 查询会议室信息列表
     *
     * @param boaConferenceRooms 会议室信息
     * @return 会议室信息集合
     */
    public List<BoaConferenceRoomsVO> selectBoaConferenceRoomsList(BoaConferenceRoomsDO boaConferenceRooms);

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
     * 批量删除会议室信息
     * 
     * @param ids 需要删除的会议室信息主键集合
     * @return 结果
     */
    public int deleteBoaConferenceRoomsByIds(Long[] ids);

    /**
     * 删除会议室信息信息
     * 
     * @param id 会议室信息主键
     * @return 结果
     */
    public int deleteBoaConferenceRoomsById(Long id);

    public String uploadImg(MultipartFile file);

    void addImgUrl(ImgUrl imgUrlVO);

    void getImgUrl(Integer id, String path, HttpServletResponse response) throws Exception;
}
