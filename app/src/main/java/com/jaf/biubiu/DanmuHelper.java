package com.jaf.biubiu;

import android.content.res.Resources;
import android.graphics.Color;

import com.jaf.jcore.Application;

import java.io.InputStream;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.android.DanmakuGlobalConfig;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;

/**
 * Created by jarrah on 2015/4/23.
 */
public class DanmuHelper {

	private IDanmakuView mDanmakuView;
	private BaseDanmakuParser mParser;

	private static DanmuHelper sDanmuHelper = new DanmuHelper();

	public static void setupDanmu(IDanmakuView view) {
		sDanmuHelper.mDanmakuView = view;
		sDanmuHelper.init();;
	}

	private BaseDanmakuParser createParser(InputStream stream) {

		if (stream == null) {
			return new BaseDanmakuParser() {

				@Override
				protected Danmakus parse() {
					return new Danmakus();
				}
			};
		}

		ILoader loader = DanmakuLoaderFactory
				.create(DanmakuLoaderFactory.TAG_BILI);

		try {
			loader.load(stream);
		} catch (IllegalDataException e) {
			e.printStackTrace();
		}
		BaseDanmakuParser parser = new BiliDanmukuParser();
		IDataSource<?> dataSource = loader.getDataSource();
		parser.load(dataSource);
		return parser;
	}

	public static void addDanmaku(IDanmakuView danmakuView, boolean islive,
			String text) {
		BaseDanmaku danmaku = DanmakuFactory.createDanmaku(
				BaseDanmaku.TYPE_SCROLL_RL, danmakuView.getWidth(),
				danmakuView.getHeight(), 10);
		if (text == null)
			danmaku.text = "这是一条弹幕, 继续点击屏幕吧" + System.nanoTime();
		else
			danmaku.text = text;

		danmaku.padding = (int) Device.dp2px(5);
		danmaku.priority = 1;
		danmaku.isLive = islive;
		danmaku.time = danmakuView.getCurrentTime() + 1250;
		danmaku.textSize = Device.sp2px(18);
		danmaku.textColor = randomColor();
		danmaku.textShadowColor = Color.parseColor("#838383");
		danmakuView.addDanmaku(danmaku);
	}

	private static int randomColor() {
		Resources resources = Application.getInstance().getApplicationContext()
				.getResources();
		int[] colors = new int[]{R.color.material_deep_teal_500,
				R.color.material_blue_grey_800, R.color.white,
				R.color.primary_text_default_material_dark};

		int max = colors.length;
		int min = 0;
		Random random = new Random();
		int index = random.nextInt(max) % (max - min + 1) + min;
		return resources.getColor(colors[index]);
	}

	public void init() {
		mParser = createParser(Application.getInstance().getResources()
				.openRawResource(R.raw.empty));
		DanmakuGlobalConfig.DEFAULT.setDanmakuStyle(
				DanmakuGlobalConfig.DANMAKU_STYLE_STROKEN, 3)
				.setDuplicateMergingEnabled(false);
		mDanmakuView.setCallback(new DrawHandler.Callback() {

			@Override
			public void updateTimer(DanmakuTimer timer) {

			}

			@Override
			public void prepared() {
				mDanmakuView.start();
			}
		});
		mDanmakuView.enableDanmakuDrawingCache(true);
		mDanmakuView.prepare(mParser);
	}
}
