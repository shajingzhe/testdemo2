package Read;

import cn.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author shajingzhe
 * @date 2023/4/10 下午2:14
 */
public class HomoloBoot {
	/**
	 * 返回一个处理完成的实体类，context中存储
	 * @param context
	 */
//	public void test(ActionContext context) {
//		JSONObject updateObj = ((JSONObjectRequestParameters) context.getParams()).getDelegateObject();
//
//		//获取entityID
//		String id = (String) context.getParams().getParameter("entityId", String.class);
//		if (StringUtils.isBlank(id)) {
//			id = (String) context.getParams().getParameter("id", String.class);
//		}
//
//		//获取type类型
//		Type type = context.getType();
//
//		//通过type及entityID获取实际实例对象
//		Entity entity = this.entityService.get(id, type);
//
//		if (entity == null) {
//			return null;
//		}
//
//
//		//判断实例类型
//		this.checkEntity(entity, context);
//		updateObj.remove("typeId");
//
//		//储存旧数据集
//		Map<String, Object> oldProps = new HashMap(entity.getProperties());
//
//		//去除不可修改对象（updateObj）1
//		JSONObject properties = updateObj.getJSONObject("properties");
//		Stream var10000 = Arrays.stream(type.getFields()).filter((f) -> {
//			return !f.isModifiable();
//		}).map(Field::getName);
//		Objects.requireNonNull(properties);
//		var10000.forEach(properties::remove);
//
//		//内存中更新旧数据集（entity）
//		EntityUpdater.updateObject(entity, updateObj);
//
//		Map<String, Object> newProps = new HashMap(entity.getProperties());
//		Map<String, Object> finProps = new HashMap();
//		finProps.putAll(oldProps);
//		finProps.putAll(newProps);
//		entity.setProperties(finProps);
//
//		//context中存入：数据库中的实例数据、旧数据集、目标修改后的数据集
//		context.setAttribute("entity", entity);
//		context.setAttribute("oldProps", oldProps);
//		context.setAttribute("finProps", finProps);
//		return entity;
//
//	}

	/**
	 * @param entity  实际
	 * @param context 传入
	 */
//	protected void checkEntity(Entity entity, ActionContext context) {
//		if (entity == null) {
//			throw new ValidationException("entity is null.");
//		}
//
//		List<Type> types = Lists.newArrayList();
//		this.getSuperType(types, entity.getType());
//		List<String> typeIds = new ArrayList();
//		Type type = entity.getType();
//		typeIds.add(type.getId());
//
//		while (type.hasSuper()) {
//			type = type.getSuper();
//			typeIds.add(type.getId());
//		}
//
//		if (!typeIds.contains(context.getType().getId())) {
//			throw new ValidationException("type not match.");
//		}
//	}

//	/**
//	 * 将parameter中的值传入至attribute中，返回修改EntityCondition；EntityCondition中没有值，则从parameter中获取
//	 * 读取传参数据,处理进行初始化，包含搜索对象、过滤条件、分页情况、排序情况、保存condition至context中
//	 *
//	 * @param context
//	 * @param condition
//	 */
//	protected void prepareQuery(ActionContext context, EntityCondition condition) {
//		RequestParameters requestParameters = context.getParams();
//		Type type = context.getType();
//		String key = (String) requestParameters.getParameter("key", String.class);
//		String value = (String) requestParameters.getParameter("value", String.class);
//		String parentId = (String) requestParameters.getParameter("parentId", String.class);
//		Boolean responseCondition = (Boolean) requestParameters.getParameter("responseCondition", Boolean.class);
//		String partWord = (String) requestParameters.getParameter("partWord", String.class);
//		String[] responseFields = (String[]) requestParameters.getParameter("responseFields", String[].class);
//		if (condition == null) {
//			condition = new EntityCondition(type.getId());
//		}
//
//		if (StringUtils.isNotBlank(parentId)) {
//			condition.setParentId(parentId);
//		}
//
//		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
//			condition.setProperty(key, value);
//		}
//
//		//将requestParameters的过滤参数放入condition中
//		EntityServiceHelper.fillConditionByExpressions(condition, requestParameters);
//		context.setAttribute("condition", condition);
//		//处理join的内容 参数名$extend_开头
//		JoinCondition joinCondition = this.extendFieldQueryHelper.handleEntityConditionAndGetJoinCondition(condition);
//		context.setAttribute("joinCondition", joinCondition);
//		context.setAttribute("partWord", partWord);
//
//		//存入排序数据及分页数据（默认排序为创建日期的创建时间）
//		ActionHelper helper = new ActionHelper(context);
//		Sort defaultSort = Sort.by(Direction.DESC, new String[]{"dateCreated"});
//		//获取分页情况，并将parameter中sort的数据处理交给PageRequest对象的属性，当没有排序时，将默认使用defaultSort的默认排序。
//		Pageable pageable = helper.getPageable(defaultSort);
//		//处理获取sort属性值||获取PageRequest中sort属性数据；
//		Sort sort = pageable.isUnpaged() ? helper.getSort(defaultSort) : pageable.getSort();
//		//检查sort属性是否合法
//		this.checkQuerySort(type, sort);
//
//
//		context.setAttribute("pageable", pageable);
//		context.setAttribute("sort", sort);
//		context.setAttribute("responseFields", responseFields);
//		context.setAttribute("responseCondition", responseCondition);
//	}
}
