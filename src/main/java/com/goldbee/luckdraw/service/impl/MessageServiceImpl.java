package com.goldbee.luckdraw.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldbee.luckdraw.entity.MessageInfo;
import com.goldbee.luckdraw.entity.TextMessage;
import com.goldbee.luckdraw.service.MessageService;
import com.goldbee.luckdraw.service.UsersService;
import com.goldbee.luckdraw.utils.MessageUtil;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private UsersService usersService;
	/**
	 * @Description 微信消息处理
	 * @author chenxm66777123
	 * @Date 2018年12月18日
	 * @version 1.0.0
	 */
	@Override
	public String messageHandel(HttpServletRequest request, HttpServletResponse response) {
		MessageInfo messageInfo = new MessageInfo();
		// 返回给微信服务器的消息,默认为null
		String respMessage = null;

		try {

			// 默认返回的文本消息内容
			String respContent = null;
			// xml分析
			// 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> map = MessageUtil.xmlToMap(request);
			// 发送方账号
			String fromUserName = map.get("FromUserName");
			messageInfo.setFromUserName(fromUserName);
			// 接受方账号（公众号）
			String toUserName = map.get("ToUserName");
			messageInfo.setToUserName(toUserName);
			// 消息类型
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			messageInfo.setMessageType(msgType);
			// 默认回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// 分析用户发送的消息类型，并作出相应的处理

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String access_token= "16_y2cROlTpjJN7RTFdAi-Ar9AduBDHV3WFanFMthfwxdbF3aUjdFZknKYEKo3egGsn08KlaUdAZtD_P4BJjNBuIYHimeRlCKuuqe4AjV45uBO8h9crqHmr3f7axIfVueGwJfQZrAydxrO8misNNZUjAJATFA";
				usersService.getUserInfoByOpenId(access_token, fromUserName);
			}

			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}

			// 事件推送(当用户主动点击菜单，或者扫面二维码等事件)
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {

				// 事件类型
				String eventType = map.get("Event");
				// 关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					// respMessage = MessageUtil.followResponseMessageModel(messageInfo);
				}
				// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// MessageUtil.cancelAttention(fromUserName);
				}
				// 扫描带参数二维码
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {

				}
				// 上报地理位置
				else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {

				}
				// 自定义菜单（点击菜单拉取消息）
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					// String eventKey=map.get("EventKey");

				}
				// 自定义菜单（(自定义菜单URl视图)）
				else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			respMessage = null;
		} finally {
			if (null == respMessage) {
				// respMessage = MessageUtil.systemErrorResponseMessageModel(messageInfo);
			}
		}

		return respMessage;
	}

}