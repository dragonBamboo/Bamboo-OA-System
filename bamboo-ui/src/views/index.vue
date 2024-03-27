<template>
  <div class="app-container home">
    <component v-if="showAdminView">
      <el-button type="primary" icon="el-icon-refresh" @click="getList">刷新</el-button>
      <el-table v-loading="loading" :data="approveList" @selection-change="handleSelectionChange">
<!--        <el-table-column type="selection" width="55" align="center"/>-->
        <el-table-column label="申请人" align="center" prop="submitName"/>
        <el-table-column label="审批状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.boa_examine_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="类型" align="center" prop="msg.approvalType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.boa_examine_type" :value="scope.row.msg.approvalType"/>
          </template>
        </el-table-column>
<!--        <el-table-column label="详情" align="center" prop="msg.message"/>-->
        <el-table-column label="详情" align="center" prop="msg">
          <template v-slot="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.msg.message" placement="bottom">
              <div>{{ shortDetail(scope.row.msg.message) }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="medium"
              type="success"
              @click="handleUpdate(scope.row)"
            >通过
            </el-button>
            <el-button
              size="medium"
              type="danger"
              @click="handleDelete(scope.row)"
            >驳回
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

      <!-- 添加或修改审批对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="提交人" prop="submitterId">
            <el-input v-model="form.submitterId" placeholder="请输入提交人"/>
          </el-form-item>
          <el-form-item label="类型与详情" prop="msg">
            <el-input v-model="form.msg" type="textarea" placeholder="请输入内容"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </component>
    <component v-else>
      <h1>欢迎访问天竹OA管理系统</h1>
    </component>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import {addApprove, delApprove, getApprove, listApprove, rejectApprove, updateApprove} from "@/api/boa/approve";

export default {
  name: "Index",
  dicts:['boa_examine_type','boa_examine_status'],
  data() {
    return {
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
      // 审批表格数据
      approveList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        submitterId: null,
        status: null,
        msg: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      userRoles: Cookies.get('user')?JSON.parse(Cookies.get('user')).roles:{},
      showAdminView: false
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.show()
  },
  methods: {
    shortDetail(msg){
      if (msg.length < 10) {
        return msg
      }
      return msg.substring(0,10)+'...'
    },
    show() {
      let roles = this.userRoles
      if (roles.length === 0) {
        return
      }
      if (roles.includes("admin")) {
        this.showAdminView = true;
      }

    },
    /** 查询审批列表 */
    getList() {
      this.loading = true;
      listApprove(this.queryParams).then(response => {
        this.approveList = response.rows.map(item=>{
          item.msg = JSON.parse(item.msg)
          return item
        });
        this.total = response.total;
        this.loading = false;
      });
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
        submitterId: null,
        status: null,
        msg: null,
        createTime: null,
        updateTime: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加审批";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // this.reset();
      const id = row.id || this.ids
      let data = row
      data.msg = JSON.stringify(data.msg)
      updateApprove(data).then(response => {
        this.$modal.msgSuccess("已通过");
        this.getList();
      });
      // getApprove(id).then(response => {
      //   this.form = response.data;
      //   this.open = true;
      //   this.title = "修改审批";
      // });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateApprove(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApprove(this.form).then(response => {
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
      let data = row
      data.msg = JSON.stringify(data.msg)
      rejectApprove(data).then(res=>{
        this.$modal.msgSuccess("已驳回")
        this.getList()
      })
      // this.$modal.confirm('是否确认删除审批编号为"' + ids + '"的数据项？').then(function() {
      //   return delApprove(ids);
      // }).then(() => {
      //   this.getList();
      //   this.$modal.msgSuccess("删除成功");
      // }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('boa/approve/export', {
        ...this.queryParams
      }, `approve_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped lang="scss">


</style>

