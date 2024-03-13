package com.bamboo.boa.service.impl;

import com.bamboo.boa.domain.BoaConferenceRoomsDO;
import com.bamboo.boa.domain.vo.BoaConferenceRoomsVO;
import com.bamboo.boa.domain.vo.ImgUrl;
import com.bamboo.boa.mapper.BoaConferenceRoomsMapper;
import com.bamboo.boa.service.IBoaConferenceRoomsService;
import com.bamboo.common.core.domain.entity.SysUser;
import com.bamboo.common.utils.DateUtils;
import com.bamboo.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 会议室信息Service业务层处理
 *
 * @author bamboo
 * @date 2024-03-07
 */
@Slf4j
@Service
public class BoaConferenceRoomsServiceImpl extends ServiceImpl<BoaConferenceRoomsMapper, BoaConferenceRoomsDO> implements IBoaConferenceRoomsService {
    @Resource
    private BoaConferenceRoomsMapper boaConferenceRoomsMapper;

    @Resource
    private ISysUserService userService;

    private BoaConferenceRoomsVO converter(BoaConferenceRoomsDO boaConferenceRooms) {
        if (boaConferenceRooms == null) {
            return null;
        }
        BoaConferenceRoomsVO vo = new BoaConferenceRoomsVO();
        BeanUtils.copyProperties(boaConferenceRooms, vo);

        Date nowDate = DateUtils.getNowDate();

        if (vo.getMeetingStartTime() != null && vo.getMeetingStartTime().getTime() > nowDate.getTime()) {
            vo.setStatus(0);
        } else {
            vo.setStatus(1);
        }

        if (vo.getMeetingEndTime() != null && vo.getMeetingEndTime().getTime() < nowDate.getTime()) {
            vo.setStatus(2);
        }

        SysUser bookingUser = userService.selectUserById(vo.getBookingPerson());
        vo.setBookingNickName(bookingUser.getNickName());
        return vo;
    }

    /**
     * 查询会议室信息
     *
     * @param id 会议室信息主键
     * @return 会议室信息
     */
    @Override
    public BoaConferenceRoomsVO selectBoaConferenceRoomsById(Long id) {
        return converter(boaConferenceRoomsMapper.selectBoaConferenceRoomsById(id));
    }

    /**
     * 查询会议室信息列表
     *
     * @param boaConferenceRooms 会议室信息
     * @return 会议室信息
     */
    @Override
    public List<BoaConferenceRoomsVO> selectBoaConferenceRoomsList(BoaConferenceRoomsDO boaConferenceRooms) {
        List<BoaConferenceRoomsDO> boaConferenceRoomsDOS = boaConferenceRoomsMapper.selectBoaConferenceRoomsList(boaConferenceRooms);
        List<BoaConferenceRoomsVO> vos = new ArrayList<>();
        boaConferenceRoomsDOS.forEach(e -> {
            if (e != null) {
                vos.add(converter(e));
            }
        });
        return vos;
    }

    /**
     * 新增会议室信息
     *
     * @param boaConferenceRooms 会议室信息
     * @return 结果
     */
    @Override
    public int insertBoaConferenceRooms(BoaConferenceRoomsDO boaConferenceRooms) {
        return boaConferenceRoomsMapper.insertBoaConferenceRooms(boaConferenceRooms);
    }

    /**
     * 修改会议室信息
     *
     * @param boaConferenceRooms 会议室信息
     * @return 结果
     */
    @Override
    public int updateBoaConferenceRooms(BoaConferenceRoomsDO boaConferenceRooms) {
        boaConferenceRooms.setUpdateTime(DateUtils.getNowDate());
        return boaConferenceRoomsMapper.updateBoaConferenceRooms(boaConferenceRooms);
    }

    /**
     * 批量删除会议室信息
     *
     * @param ids 需要删除的会议室信息主键
     * @return 结果
     */
    @Override
    public int deleteBoaConferenceRoomsByIds(Long[] ids) {
        return boaConferenceRoomsMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除会议室信息信息
     *
     * @param id 会议室信息主键
     * @return 结果
     */
    @Override
    public int deleteBoaConferenceRoomsById(Long id) {
        return boaConferenceRoomsMapper.deleteById(id);
    }

    public static final String PATH = "D:" + File.separator + "bamboo" + File.separator + "uploadImg";

    @Override
    public String uploadImg(MultipartFile file) {
        log.info("file: {}", file.getOriginalFilename());
        File path = new File(PATH);
        if (!path.exists()) {
            path.mkdirs();
        }
        String time = "" + DateUtils.getNowDate().getTime();
        String fileName = time + file.getOriginalFilename();
        try {
            file.transferTo(new File(path, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    @Override
    public void addImgUrl(ImgUrl imgUrlVO) {
        BoaConferenceRoomsDO boaConferenceRoomsDO = boaConferenceRoomsMapper.selectById(imgUrlVO.getId());
        boaConferenceRoomsDO.setImageUrl(imgUrlVO.getImgUrl());
        boaConferenceRoomsMapper.updateById(boaConferenceRoomsDO);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void getImgUrl(Integer id,String path, HttpServletResponse response) throws Exception {
        File file = new File(PATH, path);
        if (!file.exists() || !file.isFile()) {
            clearImageUrl(id);
            return;
        }
        response.reset();
        try {
            response.setContentType("image/jpeg");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Content-Length", "" + file.length());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = Files.readAllBytes(file.toPath());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());

            os.write(buffer);
            os.flush();
            os.close();
            bis.close();
        } catch (FileNotFoundException e) {
            log.error("文件读取错误");
            throw new FileNotFoundException("文件异常");
        } catch (IOException e) {
            throw new FileNotFoundException("文件异常");
        }
    }

    private void clearImageUrl(Integer id) {
        BoaConferenceRoomsDO boaConferenceRoomsDO = boaConferenceRoomsMapper.selectById(id);
        boaConferenceRoomsDO.setImageUrl(null);
        boaConferenceRoomsMapper.updateById(boaConferenceRoomsDO);
    }
}
