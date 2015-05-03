package com.jaf.biubiu;


import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;

import com.jaf.bean.BeanRequest;
import com.jaf.bean.BeanRequestAnswerList;
import com.jaf.bean.BeanRequestNearby;
import com.jaf.bean.BeanRequestPublish;
import com.jaf.bean.BeanRequestTopic;
import com.jaf.bean.BeanRequestTopicQuestionList;
import com.jaf.bean.BeanRequestUser;
import com.jaf.bean.PostAnswerComment;
import com.jaf.bean.PostAnswerQuestion;
import com.jaf.bean.PostCreateUnion;
import com.jaf.bean.PostDeleteMsg;
import com.jaf.bean.PostFeedback;
import com.jaf.bean.PostGetQuestion;
import com.jaf.bean.PostLike;
import com.jaf.bean.PostMsg;
import com.jaf.bean.PostMyQA;
import com.jaf.bean.PostRegister;
import com.jaf.bean.PostReportAbuse;
import com.jaf.jcore.Application;
import com.jaf.jcore.JacksonWrapper;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by jarrah on 2015/4/15.
 */
public class U implements Constant{
    public static JSONObject buildRequest(int cmd, double lat, double lon) {
        BeanRequest br = new BeanRequest();
        br.setAppVersion(VER);
        br.setCmd(cmd);
        br.setDvcId(Device.getId(Application.getInstance().getApplicationContext()));
        br.setLatitude(lat);
        br.setLongitude(lon);
        return JacksonWrapper.bean2Json(br);
    }

    public static JSONObject buildRegister() {
        PostRegister pr = new PostRegister();
        pr = (PostRegister) buildBaseRequest(pr, CMD.USER_REG);
        pr.setAlias(Device.getId(Application.getInstance().getApplicationContext()));
        return JacksonWrapper.bean2Json(pr);
    }

    public static JSONObject buildTopicQuestion(boolean fresh, int lastId, int unionId) {
        BeanRequestTopicQuestionList brn = new BeanRequestTopicQuestionList();
        brn.setAppVersion(VER);
        brn.setCmd(CMD.LIST_TOPIC_QUESTION);
        brn.setDvcId(Device.getId(Application.getInstance().getApplicationContext()));
        brn.setLatitude(Application.getInstance().getAppExtraInfo().lat);
        brn.setLongitude(Application.getInstance().getAppExtraInfo().lon);
        brn.setIdType(fresh ? 2 : 1);
        brn.setLastId(lastId);
        brn.setUnionId(unionId);
        return JacksonWrapper.bean2Json(brn);
    }

    public static JSONObject buildNearby(double lat, double lon, boolean fresh, int lastId) {
        BeanRequestNearby brn = new BeanRequestNearby();
        brn.setAppVersion(VER);
        brn.setCmd(CMD.LIST_NEARBY);
        brn.setDvcId(Device.getId(Application.getInstance().getApplicationContext()));
        brn.setLatitude(lat);
        brn.setLongitude(lon);
        brn.setIdType(fresh ? 2 : 1);
        brn.setLastId(lastId);
        return JacksonWrapper.bean2Json(brn);
    }

    public static String b642s(String str) {
        try {
            byte[] data = Base64.decode(str, Base64.DEFAULT);
            String text = new String(data, "UTF-8");
            return text;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    public static JSONObject buildTopic(boolean fresh, int lastId, int type) {
        BeanRequestTopic brt = new BeanRequestTopic();
        brt.setAppVersion(VER);
        brt.setCmd(CMD.LIST_UNION);
        brt.setDvcId(Device.getId(Application.getInstance().getApplicationContext()));
        brt.setLatitude(Application.getInstance().getAppExtraInfo().lat);
        brt.setLongitude(Application.getInstance().getAppExtraInfo().lon);
        brt.setIdType(fresh ? 0 : 1);
        brt.setLastId(lastId);
        brt.setType(type);
        return JacksonWrapper.bean2Json(brt);
    }

    public static JSONObject buildUser() {
        BeanRequestUser bru = new BeanRequestUser();
        bru.setDvcId(Device.getId(Application.getInstance().getApplicationContext()));
        bru.setCmd(CMD.USER_INFO);
        bru.setAppVersion(VER);
        return JacksonWrapper.bean2Json(bru);
    }

    public static JSONObject buildQuestion(boolean fresh, int lastId, int questId) {
        BeanRequestAnswerList brq = new BeanRequestAnswerList();
        brq.setAppVersion(VER);
        brq.setCmd(CMD.LIST_QUESTION);
        brq.setDvcId(Device.getId(Application.getInstance().getApplicationContext()));
        brq.setIdType(fresh ? 0 : 1);
        brq.setLastId(lastId);
        brq.setQuestId(questId);
        return JacksonWrapper.bean2Json(brq);
    }

    public static BeanRequestAnswerList buildAnswerArgs(boolean fresh, int lastId, int questId) {
        BeanRequestAnswerList brq = new BeanRequestAnswerList();
//        brq.setCmd(CMD.LIST_QUESTION);
        brq.setIdType(fresh ? 0 : 1);
        brq = (BeanRequestAnswerList) buildBaseRequest(brq, CMD.LIST_QUESTION);
        brq.setLastId(lastId);
        brq.setQuestId(questId);
        return brq;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static BeanRequestPublish buildPublishQuestion(String sign, String locDesc, int questType, int unionId, String quest) {
        BeanRequestPublish brp = new BeanRequestPublish();
        brp = (BeanRequestPublish) buildBaseRequest(brp, CMD.PUBLISH_QUESTION);
        brp.setSign(sign);
        brp.setSelfLocDesc(locDesc);
        brp.setQuestType(questType);
        brp.setUnionId(unionId);
        brp.setQuest(quest);
        return brp;
    }

    public static BeanRequest buildBaseRequest(BeanRequest br, int cmd) {
        br.setAppVersion(VER);
        br.setCmd(cmd);
        br.setDvcId(Device.getId(Application.getInstance().getApplicationContext()));
        br.setLatitude(Application.getInstance().getAppExtraInfo().lat);
        br.setLongitude(Application.getInstance().getAppExtraInfo().lon);
        return br;
    }

    public static BeanRequestTopicQuestionList buildTopicQuestionListArg(int unionId) {
        BeanRequestTopicQuestionList brq = new BeanRequestTopicQuestionList();
        brq.setAppVersion(VER);
        brq.setCmd(CMD.LIST_TOPIC_QUESTION);
        brq.setDvcId(Device.getId(Application.getInstance().getApplicationContext()));
        brq.setIdType(0);
        brq.setLastId(0);
        brq.setUnionId(unionId);
        return brq;
    }

    public static JSONObject buildPostAnswerQuestion(String ans, String selLocDesc, int questionId) {
        PostAnswerQuestion post = new PostAnswerQuestion();
        post = (PostAnswerQuestion) buildBaseRequest(post, CMD.POST_ANSWER_QUESTION);
        post.setSelfLocDesc(selLocDesc);
        post.setQuestId(questionId);
        post.setAns(ans);
        return JacksonWrapper.bean2Json(post);
    }

    public static JSONObject buildPostLike(boolean isLike, int qid, int aid) {
        PostLike pl = new PostLike();
        pl = (PostLike) buildBaseRequest(pl, CMD.POST_LIKE);
        pl.setQuestId(qid);
        pl.setAnsId(aid);
        pl.setLike(isLike ? 1 : 2);
        return JacksonWrapper.bean2Json(pl);
    }

    public static JSONObject buildMsgList(boolean fresh, int lastId) {
        PostMsg postMsg = new PostMsg();
        postMsg.setIdType(fresh ? 0 : 1);
        postMsg.setLastId(lastId);
        postMsg = (PostMsg) buildBaseRequest(postMsg, CMD.POST_MSG);
        return JacksonWrapper.bean2Json(postMsg);
    }

    public static JSONObject buildMyQList(boolean fresh, int lastId) {
        PostMyQA post = new PostMyQA();
        post.setIdType(fresh ? 2 : 1);
        post.setLastId(lastId);
        post = (PostMyQA) buildBaseRequest(post, CMD.POST_MY_Q);
        return JacksonWrapper.bean2Json(post);
    }

    public static JSONObject buildMyAList(boolean fresh, int lastId) {
        PostMyQA post = new PostMyQA();
        post.setIdType(fresh ? 0 : 1);
        post.setLastId(lastId);
        post = (PostMyQA) buildBaseRequest(post, CMD.POST_MY_A);
        return JacksonWrapper.bean2Json(post);
    }

    public static String ToUTF8(String str) {
        Charset UTF8_CHARSET = Charset.forName("UTF-8");
        return new String(str.getBytes(), UTF8_CHARSET);
    }

    public static JSONObject buildPostFeedback(String comment) {
        PostFeedback pf = new PostFeedback();
        pf = (PostFeedback) U.buildBaseRequest(pf, CMD.POST_FEEDBACK);
        PackageInfo pInfo = null;
        try {
            pInfo = Application.getInstance().getPackageManager().getPackageInfo(Application.getInstance().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        pf.setVerId(pInfo == null ? 1 : pInfo.versionCode);
        pf.setComment(comment);
        String osVer = "android-" + android.os.Build.VERSION.SDK_INT;
        pf.setOsVer(osVer);
        return JacksonWrapper.bean2Json(pf);
    }

    public static JSONObject buildPostFeedBackRandom() {
        BeanRequest br = new BeanRequest();
        br = buildBaseRequest(br, CMD.RANDOM_FEEDBACK);
        return JacksonWrapper.bean2Json(br);
    }

    public static JSONObject buildRandomTopic() {
        BeanRequest br = new BeanRequest();
        br = buildBaseRequest(br, CMD.RANDOM_TOPIC);
        return JacksonWrapper.bean2Json(br);
    }

    public static JSONObject buildReportAbuse(int contId, int contType, String content) {
        PostReportAbuse pra = new PostReportAbuse();
        pra = (PostReportAbuse) buildBaseRequest(pra, CMD.REPORT_ABUSE);
        pra.setContId(contId);
        pra.setContType(contType);
        pra.setReason(1);
        pra.setReasonCont(content);
        return JacksonWrapper.bean2Json(pra);
    }

    public static JSONObject postAnswerComment(String ans, int ansId, int qid, String selLocDesc) {
        PostAnswerComment pac = new PostAnswerComment();
        pac = (PostAnswerComment) buildBaseRequest(pac, CMD.POST_ANSWER_QUESTION);
        pac.setQuestId(qid);
        pac.setAns(ans);
        pac.setAnsId(ansId);
        pac.setToNick("");
        pac.setSelfLocDesc(selLocDesc);
        return JacksonWrapper.bean2Json(pac);
    }

    public static JSONObject postCreateUnion(String unionName, String selfLocDesc, String picPath) {
        PostCreateUnion pcu = new PostCreateUnion();
        pcu = (PostCreateUnion) buildBaseRequest(pcu, CMD.POST_CREATE_UNION);
        pcu.setSelfLocDesc(selfLocDesc);
        pcu.setPicPath(picPath);
        pcu.setUnionName(unionName);
        return JacksonWrapper.bean2Json(pcu);
    }

    public static JSONObject buildGetSchoolName() {
        BeanRequest beanRequest = new BeanRequest();
        beanRequest = buildBaseRequest(beanRequest, CMD.GET_SPECIAL_NAME);
        return JacksonWrapper.bean2Json(beanRequest);
    }

    public static JSONObject buildGetQuestion(int questionId) {
        PostGetQuestion pgq = new PostGetQuestion();
        pgq.setQuestId(questionId);
        pgq = (PostGetQuestion) buildBaseRequest(pgq, CMD.POST_GET_QUESTION);
        return JacksonWrapper.bean2Json(pgq);
    }


    public static JSONObject buildDelete(int _did) {
        PostDeleteMsg pdm = new PostDeleteMsg();
        pdm = (PostDeleteMsg) buildBaseRequest(pdm, CMD.POST_DELETE_MSG);
        pdm.setDelId(new ArrayList<Integer>());
        pdm.getDelId().add(_did);
        return JacksonWrapper.bean2Json(pdm);
    }
}
