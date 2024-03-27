<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会议名" prop="meetingName">
        <el-input
          v-model="queryParams.meetingName"
          placeholder="请输入会议名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会议地" prop="detailedLocation">
        <el-input
          v-model="queryParams.detailedLocation"
          placeholder="请输入会议室的详细位置信息"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预定人员" prop="bookingPerson">
        <el-input
          v-model="queryParams.bookingPerson"
          placeholder="请输入预定人员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="meetingStartTime">
        <el-date-picker clearable
                        v-model="queryParams.meetingStartTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择会议的开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="meetingEndTime">
        <el-date-picker clearable
                        v-model="queryParams.meetingEndTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择会议的结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['boa:rooms:add']"
        >新增
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="success"-->
      <!--          plain-->
      <!--          icon="el-icon-edit"-->
      <!--          size="mini"-->
      <!--          :disabled="single"-->
      <!--          @click="handleUpdate"-->
      <!--          v-hasPermi="['boa:rooms:edit']"-->
      <!--        >修改-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['boa:rooms:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['boa:rooms:export']"
        >导出
        </el-button>
      </el-col>
      <!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    </el-row>

    <el-table v-loading="loading" :data="roomsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="会议名" align="center" prop="meetingName"/>
      <el-table-column label="预览" align="center" prop="imageUrl">
        <template v-slot="scope">
          <el-image
            v-if="scope.row.imageUrl"
            :src="getElImageUrl(scope.row)"/>
<!--            src="process.env.VUE_APP_BASE_API+`/dev-api/boa/rooms/imgUrl/${scope.row.id}/${scope.row.imageUrl}`"/>-->
          <el-upload
            v-else
            class="upload-demo"
            :http-request="uploadImg"
            :on-success="handleUploadSuccess.bind(null, scope.row)"
            :show-file-list="false">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="地点" align="center" prop="detailedLocation"/>
      <el-table-column label="预定人员" align="center" prop="bookingNickName"/>
      <el-table-column label="状态" align="center" prop="roomStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.boa_rooms_status" :value="scope.row.status"/>
        </template>
      </el-table-column>

      <!--      <el-table-column label="会议室预览" align="center" prop="imageUrl"/>-->
      <el-table-column label="开始时间" align="center" prop="meetingStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.meetingStartTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="meetingEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.meetingEndTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['boa:rooms:edit']"
          >预定
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['boa:rooms:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会议室信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会议名" prop="meetingName">
          <el-input v-model="form.meetingName" placeholder="请输入会议名"/>
        </el-form-item>
        <el-form-item label="会议室" prop="detailedLocation">
          <el-input v-model="form.detailedLocation" placeholder="请输入会议室的详细位置信息"/>
        </el-form-item>
        <el-form-item label="预定人员" prop="bookingPerson">
          <!--          <el-input v-model="form.bookingPerson" placeholder="请输入预定会议室的人员的ID"/>-->
          <el-select v-model="form.bookingPerson" placeholder="请选择预定人员">
            <el-option
              v-for="item in bookingPersons"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="会议室预览" prop="imageUrl">-->
        <!--          &lt;!&ndash;          <el-input v-model="form.imageUrl" placeholder="请输入会议室图片的URL地址，存储实际路径" />&ndash;&gt;-->
        <!--          <el-upload-->
        <!--            action="uploadImage()"-->
        <!--            list-type="picture-card"-->
        <!--            :auto-upload="true">-->
        <!--            <i slot="default" class="el-icon-plus"></i>-->
        <!--            <div slot="file" slot-scope="{file}">-->
        <!--              <img-->
        <!--                class="el-upload-list__item-thumbnail"-->
        <!--                :src="file.url" alt=""-->
        <!--              >-->
        <!--              <span class="el-upload-list__item-actions">-->
        <!--                <span-->
        <!--                  class="el-upload-list__item-preview"-->
        <!--                  @click="handlePictureCardPreview(file)"-->
        <!--                >-->
        <!--                  <i class="el-icon-zoom-in"></i>-->
        <!--                </span>-->
        <!--                <span-->
        <!--                  v-if="!disabled"-->
        <!--                  class="el-upload-list__item-delete"-->
        <!--                  @click="handleDownload(file)"-->
        <!--                >-->
        <!--                  <i class="el-icon-download"></i>-->
        <!--                </span>-->
        <!--                <span-->
        <!--                  v-if="!disabled"-->
        <!--                  class="el-upload-list__item-delete"-->
        <!--                  @click="handleRemove(file)"-->
        <!--                >-->
        <!--                  <i class="el-icon-delete"></i>-->
        <!--                </span>-->
        <!--              </span>-->
        <!--            </div>-->
        <!--          </el-upload>-->
        <!--          <el-dialog :visible.sync="dialogVisible">-->
        <!--            <img width="100%" :src="dialogImageUrl" alt="">-->
        <!--          </el-dialog>-->
        <!--        </el-form-item>-->
        <el-form-item label="开始时间" prop="meetingStartTime">
          <el-date-picker clearable
                          v-model="form.meetingStartTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择会议的开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="meetingEndTime">
          <el-date-picker clearable
                          v-model="form.meetingEndTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择会议的结束时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listRooms, getRooms, delRooms, addRooms, updateRooms, uploadFile, addImagUrl} from "@/api/boa/rooms";
import {userSmallList} from "@/api/boa/common";
import axios from "axios";
import {getToken} from "@/utils/auth";

export default {
  name: "Rooms",
  dicts: ['boa_rooms_status'],
  data() {
    return {
      // 全部人员
      bookingPersons: [],
      // 会议室图片
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 会议室信息表格数据
      roomsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        meetingName: null,
        detailedLocation: null,
        bookingPerson: null,
        imageUrl: null,
        meetingStartTime: null,
        meetingEndTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      imgUrl:null
    };
  },
  mounted() {
    this.getUserSmall()
  },
  created() {
    this.getList();
  },
  methods: {
    getElImageUrl(row) {
      let url = `http://localhost:8081/boa/rooms/imgUrl/${row.id}/${row.imageUrl}`
      console.log('url',url)
      return url
    },
    getImageUrl() {
      window.open(this.imgUrl, '_blank')
    },
    uploadImg(file) {
      console.log(file)
      let formData = new FormData();
      formData.append('file', file.file)

      axios.post(process.env.VUE_APP_BASE_API+'/boa/rooms/uploadImg', formData, {
        headers: {
          'Authorization': 'Bearer ' + getToken(),
          'Content-Type': 'multipart/form-data'
        }
      }).then((response) => {
        // if (response.code === 200) {
        //   // 如果上传成功
        //   console.log('success')
        //   file.onSuccess(response.data);
        // } else {
        //   // 如果上传失败
        //   file.onError(response.data);
        // }
        file.onSuccess(response.data);
      })
        .catch((error) => {
          console.error(error);
          file.onError(error);
        });
    },
    handleUploadSuccess(row, response, file, fileList) {
      let param = {
        id: row.id,
        imgUrl: response.data
      }
      console.log('param:',param)
      addImagUrl(param)
        .then(res=>{
          this.getList()
        })
    },
    // 获取基本人员信息
    getUserSmall() {
      userSmallList().then(res => {
        this.bookingPersons = res.data
      })
    },
    /** 会议室图片逻辑处理 */
    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload(file) {
      console.log(file);
    },
    /** 查询会议室信息列表 */
    getList() {
      this.loading = true;
      listRooms(this.queryParams).then(response => {
        this.roomsList = response.rows
        //   .map(item=>{
        //   if (item.imageUrl !== null) {
        //     item.url = this.getImg(item.id,item.imageUrl)
        //   }
        //   return item
        // })
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取图片
    getImg(id,imageUrl){
      axios.get(process.env.VUE_APP_BASE_API+`/boa/rooms/imgUrl/${id}/${imageUrl}`, {
        headers: {
          'Authorization': 'Bearer ' + getToken(),
          responseType: 'blob'
        }
      }).then((res) => {
        console.log('res',res)
        let binaryData = []
        binaryData.push(res)
        let blobData = new Blob(binaryData,{type: res.headers['content-type']})
        let blob = window.URL.createObjectURL(blobData)
        this.imgUrl = blob
        console.log("blob: ",blob)
        return res
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        meetingName: null,
        detailedLocation: null,
        bookingPerson: null,
        imageUrl: null,
        meetingStartTime: null,
        meetingEndTime: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加会议室信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRooms(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "预定会议室";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRooms(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRooms(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return delRooms(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('boa/rooms/export', {
        ...this.queryParams
      }, `rooms_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
