package cn.jake.share.frdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import cn.jake.share.frdialog.R;


/**
 * Created by jack on 2018/1/22
 */

public class FRDialog extends Dialog {

    public FRDialogController controller;

    public FRDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        controller = new FRDialogController(this, getWindow());
    }

    public <T extends View> T getView(int viewId) {
        return controller.getView(viewId);
    }

    public void setText(int id, CharSequence charSequence) {
        controller.setText(id, charSequence);
    }

    public void setOnClickListener(int id, View.OnClickListener onClickListener) {
        controller.setOnClickListener(id, onClickListener);
    }

    public void setMaterialDesignPositiveListener(View.OnClickListener onClickListener) {
        controller.setMaterialDesignPositiveListener(R.id.dialog_material_tv_confirm, onClickListener);
    }

    public void setMaterialDesignNegativeListener(View.OnClickListener onClickListener) {
        controller.setMaterialDesignNegativeListener(R.id.dialog_material_tv_cancel, onClickListener);
    }

    public static class Builder {

        FRDialogController.FRDialogParams params;
        MDBuilder mdBuilder;
        CommonBuilder commonBuilder;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            params = new FRDialogController.FRDialogParams(context, themeResId);
        }

        public MDBuilder setMdBuilder() {
            mdBuilder = new MDBuilder(params, this);
            return mdBuilder;
        }

        public CommonBuilder setCommonBuilder() {
            commonBuilder = new CommonBuilder(params, this);
            return commonBuilder;
        }

        //设置dialog宽度全屏
        public Builder setFullWidth() {
            params.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        //设置dialog宽高
        public Builder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        //设置dialog从底部弹出
        public Builder setFromBottom(boolean isAnimation) {
            if (isAnimation) {
                params.mAnimation = R.style.dialog_from_bottom_anim;
            }
            params.mGravity = Gravity.BOTTOM;
            return this;
        }

        //设置dialog默认动画
        public Builder setDefaultAnim() {
            params.mAnimation = R.style.default_dialog_anim;
            return this;
        }

        //设置dialog其他动画
        public Builder setAnimation(int animation) {
            params.mAnimation = animation;
            return this;
        }

        public FRDialog create() {
            FRDialog dialog = new FRDialog(params.mContext, params.mThemeResId);
            params.apply(dialog.controller);
            dialog.setCanceledOnTouchOutside(params.mCancelableOutside);
            dialog.setCancelable(params.mCancelable);
            dialog.setOnCancelListener(params.mOnCancelListener);
            dialog.setOnDismissListener(params.mOnDismissListener);
            if (params.mOnKeyListener != null) {
                dialog.setOnKeyListener(params.mOnKeyListener);
            }
            return dialog;
        }

        public FRDialog show() {
            final FRDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

    public static class CommonBuilder {
        FRDialogController.FRDialogParams params;
        Builder builder;

        public CommonBuilder(FRDialogController.FRDialogParams params, Builder builder) {
            this.params = params;
            this.builder = builder;
        }

        //设置dialog布局文件
        public CommonBuilder setContentView(@LayoutRes int layoutRes) {
            params.mLayoutRes = layoutRes;
            params.mContentView = null;
            return this;
        }

        //设置dialog布局
        public CommonBuilder setContentView(View view) {
            params.mLayoutRes = 0;
            params.mContentView = view;
            return this;
        }

        //设置文字（dialog上任何一个控件）
        public CommonBuilder setText(int id, CharSequence charSequence) {
            params.mTextArray.put(id, charSequence);
            return this;
        }

        //设置点击事件（dialog上任何一个控件）
        public CommonBuilder setOnClickListener(int id, View.OnClickListener onClickListener) {
            params.mClickListenerArray.put(id, onClickListener);
            return this;
        }

        //设置dialog宽度全屏
        public CommonBuilder setFullWidth() {
            params.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        //设置dialog宽高
        public CommonBuilder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        //设置dialog从底部弹出
        public CommonBuilder setFromBottom(boolean isAnimation) {
            if (isAnimation) {
                params.mAnimation = R.style.dialog_from_bottom_anim;
            }
            params.mGravity = Gravity.BOTTOM;
            return this;
        }

        //设置dialog默认动画
        public CommonBuilder setDefaultAnim() {
            params.mAnimation = R.style.default_dialog_anim;
            return this;
        }

        //设置dialog其他动画
        public CommonBuilder setAnimation(int animation) {
            params.mAnimation = animation;
            return this;
        }

        public FRDialog show() {
            return builder.show();
        }
    }

    public static class MDBuilder {

        FRDialogController.FRDialogParams params;
        Builder builder;

        public MDBuilder(FRDialogController.FRDialogParams params, Builder builder) {
            this.params = params;
            this.builder = builder;
        }

        //设置dialog为materialDesign（布局确定，简单的MD效果）
        public MDBuilder setMaterialDesign() {
            params.mIsMaterialDesign = true;
            params.mLayoutRes = R.layout.dialog_material;
            return this;
        }

        //设置MD效果dialog的头部
        public MDBuilder setMaterialDesignTitle(CharSequence charSequence) {
            params.mMaterialDesignTitle = charSequence;
            return this;
        }

        //设置MD效果dialog内容
        public MDBuilder setMaterialDesignContent(CharSequence charSequence) {
            params.mMaterialDesignContent = charSequence;
            return this;
        }

        //设置MD效果dialog取消和确认键文字
        public MDBuilder setMaterialDesignNegativeAndPositive(CharSequence... charSequence) {
            if (charSequence.length > 0) {
                params.mNegativeContent = charSequence[0];
            }
            if (charSequence.length > 1) {
                params.mPositiveContent = charSequence[1];
            }
            return this;
        }

        ////设置MD效果dialog取消和确认键文字颜色
        public MDBuilder setMaterialDesignNegativeAndPositiveTextColor(Integer... colors) {
            if (colors.length > 0) {
                params.mNegativeTextColor = colors[0];
            }
            if (colors.length > 1) {
                params.mPositiveTextColor = colors[1];
            }
            return this;
        }

        //设置MD效果dialog确认键点击事件
        public MDBuilder setMaterialDesignPositiveListener(View.OnClickListener onClickListener) {
            params.mPositiveListener = onClickListener;
            return this;
        }

        //设置MD效果dialog取消键点击事件（默认不设置的效果为弹窗消失）
        public MDBuilder setMaterialDesignNegativeListener(View.OnClickListener onClickListener) {
            params.mNegativeListener = onClickListener;
            return this;
        }

        //设置dialog宽度全屏
        public MDBuilder setFullWidth() {
            params.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        //设置dialog宽高
        public MDBuilder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        //设置dialog从底部弹出
        public MDBuilder setFromBottom(boolean isAnimation) {
            if (isAnimation) {
                params.mAnimation = R.style.dialog_from_bottom_anim;
            }
            params.mGravity = Gravity.BOTTOM;
            return this;
        }

        //设置dialog默认动画
        public MDBuilder setDefaultAnim() {
            params.mAnimation = R.style.default_dialog_anim;
            return this;
        }

        //设置dialog其他动画
        public MDBuilder setAnimation(int animation) {
            params.mAnimation = animation;
            return this;
        }

        public FRDialog show() {
            return builder.show();
        }
    }
}