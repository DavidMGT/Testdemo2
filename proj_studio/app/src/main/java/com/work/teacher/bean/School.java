package com.work.teacher.bean;

import java.io.Serializable;

/**
 * 学校
 *@author 左丽姬
 */
public class School implements Serializable {
	
		private int status;
		private String userId;
		private String schoolCode;
		private String schoolName;
		private String userPessmion;
		private String zhuce;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getSchoolCode() {
			return schoolCode;
		}
		public void setSchoolCode(String schoolCode) {
			this.schoolCode = schoolCode;
		}
		public String getSchoolName() {
			return schoolName;
		}
		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}
		public String getUserPessmion() {
			return userPessmion;
		}
		public void setUserPessmion(String userPessmion) {
			this.userPessmion = userPessmion;
		}
		public String getZhuce() {
			return zhuce;
		}
		public void setZhuce(String zhuce) {
			this.zhuce = zhuce;
		}
		public School() {
			super();
		}
		public School(int status, String userId, String schoolCode, String schoolName, String userPessmion,
				String zhuce) {
			super();
			this.status = status;
			this.userId = userId;
			this.schoolCode = schoolCode;
			this.schoolName = schoolName;
			this.userPessmion = userPessmion;
			this.zhuce = zhuce;
		}
		@Override
		public String toString() {
			return "School [status=" + status + ", userId=" + userId + ", schoolCode=" + schoolCode + ", schoolName="
					+ schoolName + ", userPessmion=" + userPessmion + ", zhuce=" + zhuce + "]";
		}

}
