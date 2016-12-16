/**
 * 项目根路径
 * 
 * @param baseURI
 * @returns
 */
var baseURI = "";
var pramTitle = "";
function setConBaseURI(baseURI) {
	this.baseURI = baseURI;
}
function setParamTitle(pramTitle) {
	this.pramTitle = pramTitle;
}

/**
 * 初始化机构树下拉列表
 * 
 * @param id
 *            标签ID
 * @param initValue
 *            初始值
 */
function initOrgCombotree(id, initValue) {
	$('#' + id).combotree({
		url : baseURI + '/sys/organization/tree',
		parentField : 'pid',
		required : true,
		panelHeight : '200',
		panelWidth : '280',
		value : initValue
	});
}

/**
 * 查看项目信息
 * 
 * @param projectNo
 *            项目编号
 */
function viewProjectFun(projectNo) {
	self.parent.addTab({
		url : baseURI + '/biz/project/viewPage?projectNo=' + projectNo
				+ "&title=${param.title}",
		title : "项目信息",
		iconCls : "icon-add"
	});
}

/**
 * 查看合同信息
 * 
 * @param contractNo
 *            合同编号
 */
function viewContractFun(contractNo) {
	self.parent.addTab({
		url : baseURI + '/biz/contract/viewPage?contractNo=' + contractNo
				+ "&title=${param.title}",
		title : "合同信息",
		iconCls : "icon-add"
	});
}

/**
 * 查看户型信息
 * 
 * @param houseTypeNo
 *            户型编号
 */
function viewHouseTypeFun(houseTypeNo) {
	self.parent.addTab({
		url : baseURI + '/biz/houseType/viewPage?houseTypeNo=' + houseTypeNo
				+ "&title=${param.title}",
		title : "户型信息",
		iconCls : "icon-add"
	});
}
/**
 * 查看业主信息
 * 
 * @param ownerNo
 *            业主编号
 */
function viewOwnerFun(ownerNo) {
	self.parent.addTab({
		url : baseURI + '/biz/owner/viewPage?ownerNo=' + ownerNo
				+ "&title=${param.title}",
		title : "业主信息",
		iconCls : "icon-add"
	});
}