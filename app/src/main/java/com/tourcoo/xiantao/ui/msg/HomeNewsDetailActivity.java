package com.tourcoo.xiantao.ui.msg;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.GoodsCollectEntity;
import com.tourcoo.xiantao.entity.message.MessageBean;
import com.tourcoo.xiantao.entity.news.NewsBean;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.msg.MsgSystemActivity.EXTRA_MESSAGE_DETAIL;

/**
 * @author :JenkinsZhou
 * @description :消息详情
 * @company :途酷科技
 * @date 2019年04月29日19:33
 * @Email: 971613168@qq.com
 */
public class HomeNewsDetailActivity extends BaseTourCooTitleActivity {

    private WebView webView;

    @Override
    public int getContentLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("鲜淘快报");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        int id = getIntent().getIntExtra("id",0);
        webView = findViewById(R.id.webView);
        getNewsDetails(id);
    }


    /**
     * 查询news详情
     */
    private void getNewsDetails(int id) {
        ApiRepository.getInstance().getNewsDetails(id).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<NewsBean>>() {
                    @Override
                    public void onRequestNext(BaseEntity<NewsBean> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                imageFillWidth(webView, entity.data.getContent());
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                    }
                });
    }

    /**
     * 处理图片视频填充手机宽度
     */
    private void imageFillWidth(WebView webView, String content) {
        Document doc = Jsoup.parse(content);

        //修改视频标签
        Elements embeds = doc.getElementsByTag("embed");
        for (Element element : embeds) {
            //宽度填充手机，高度自适应
            element.attr("width", "100%").attr("height", "auto");
        }
        //webview 无法正确识别 embed 为视频，所以这里把这个标签改成 video 手机就可以识别了
        doc.select("embed").tagName("video");

        //控制图片的大小
        Elements imgs = doc.getElementsByTag("img");
        for (int i = 0; i < imgs.size(); i++) {
            //宽度填充手机，高度自适应
            imgs.get(i).attr("style", "width: 100%; height: auto;");
        }

        //对数据进行包装,除去WebView默认存在的一定像素的边距问题
        String data = "<html><head><style>img{width:100% !important;}</style></head><body style='margin:0;padding:0'>" + doc + "</body></html>";

        //加载使用 jsoup 处理过的 html 文本
        webView.loadData(data, "text/html; charset=UTF-8", null);
    }

}
