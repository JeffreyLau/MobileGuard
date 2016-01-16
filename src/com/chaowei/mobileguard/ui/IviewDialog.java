package com.chaowei.mobileguard.ui;

import com.chaowei.mobileguard.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class IviewDialog extends Dialog {

	public IviewDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public IviewDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public IviewDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public static class Builder {
		private Context mContext;
		private CharSequence mTitle;
		private CharSequence mMessage;
		private CharSequence mPositiveButtonText;
		private CharSequence mNegativeButtonText;
		private int mImageViewId = -1;

		private View mContentView;

		private DialogInterface.OnClickListener mPositiveButtonListener,
				mNegativeButtonListener;

		public Builder(Context context) {
			this.mContext = context;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * contentView is not added to the Dialog...
		 * 
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.mContentView = v;
			return this;
		}

		/**
		 * Set the title using the given resource id.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setImageViewId(int imageViewId) {
			this.mImageViewId = imageViewId;
			return this;
		}

		/**
		 * Set the title using the given resource id.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setTitle(int titleId) {
			this.mTitle = mContext.getText(titleId);
			return this;
		}

		/**
		 * Set the title displayed in the {@link Dialog}.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setTitle(CharSequence title) {
			this.mTitle = title;
			return this;
		}

		/**
		 * Set the message to display using the given resource id.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setMessage(int messageId) {
			this.mMessage = mContext.getText(messageId);
			return this;
		}

		/**
		 * Set the message to display.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setMessage(CharSequence message) {
			this.mMessage = message;
			return this;
		}

		/**
		 * Set a listener to be invoked when the positive button of the dialog
		 * is pressed.
		 * 
		 * @param textId
		 *            The resource id of the text to display in the positive
		 *            button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setPositiveButton(int textId,
				final OnClickListener listener) {
			this.mPositiveButtonText = mContext.getText(textId);
			this.mPositiveButtonListener = listener;
			return this;
		}

		/**
		 * Set a listener to be invoked when the positive button of the dialog
		 * is pressed.
		 * 
		 * @param text
		 *            The text to display in the positive button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setPositiveButton(CharSequence text,
				final OnClickListener listener) {
			this.mPositiveButtonText = text;
			this.mPositiveButtonListener = listener;
			return this;
		}

		/**
		 * Set a listener to be invoked when the negative button of the dialog
		 * is pressed.
		 * 
		 * @param textId
		 *            The resource id of the text to display in the negative
		 *            button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setNegativeButton(int textId,
				final OnClickListener listener) {
			this.mNegativeButtonText = mContext.getText(textId);
			this.mNegativeButtonListener = listener;
			return this;
		}

		/**
		 * Set a listener to be invoked when the negative button of the dialog
		 * is pressed.
		 * 
		 * @param text
		 *            The text to display in the negative button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setNegativeButton(CharSequence text,
				final OnClickListener listener) {
			this.mNegativeButtonText = text;
			this.mNegativeButtonListener = listener;
			return this;
		}

        /**
         * Creates a {@link AlertDialog} with the arguments supplied to this builder and
         * {@link Dialog#show()}'s the dialog.
         */
        public IviewDialog show() {
        	IviewDialog dialog = create();
            dialog.show();
            return dialog;
        }	
		
		
		/**
		 * Creates a {@link AlertDialog} with the arguments supplied to this
		 * builder. It does not {@link Dialog#show()} the dialog. This allows
		 * the user to do any extra processing before displaying the dialog. Use
		 * {@link #show()} if you don't have any other processing to do and want
		 * this to be created and displayed.mTitle
		 */
		public IviewDialog create() {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final IviewDialog mIviewDialog = new IviewDialog(mContext,
					R.style.IviewDialog);
			mIviewDialog.getWindow().setWindowAnimations(
					R.style.iviewDialogWindowAnim);

			// 设置触摸对话框意外的地方不能取消对话框
			mIviewDialog.setCanceledOnTouchOutside(false);
			// 阻止返回键响应
			mIviewDialog.setCancelable(false);
			// load xml to view
			View iview_dialog_layout = inflater.inflate(R.layout.iview_dialog,
					null);

			mIviewDialog.addContentView(iview_dialog_layout, new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			// 1 set title and message
			ImageView iview_dialog_tlog = (ImageView) iview_dialog_layout
					.findViewById(R.id.iview_dialog_tlog);

			TextView iview_dialog_title = (TextView) iview_dialog_layout
					.findViewById(R.id.iview_dialog_title);
			TextView iview_dialog_message = (TextView) iview_dialog_layout
					.findViewById(R.id.iview_dialog_message);
			Button iviewConfirmButton = (Button) iview_dialog_layout
					.findViewById(R.id.positiveButton);
			Button iviewConcelButton = (Button) iview_dialog_layout
					.findViewById(R.id.negativeButton);
			if (mImageViewId != -1)
				iview_dialog_tlog.setImageResource(mImageViewId);
			else
				iview_dialog_tlog.setVisibility(View.GONE);

			if (mTitle != null) {
				iview_dialog_title.setText(mTitle);
			} else {
				iview_dialog_title.setText("请输入title");
			}
			// set the confirm button
			if (mPositiveButtonText != null) {
				iviewConfirmButton.setText(mPositiveButtonText);
				if (mPositiveButtonListener != null) {
					iviewConfirmButton
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									mPositiveButtonListener.onClick(
											mIviewDialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				iviewConfirmButton.setText("确定");
			}

			if (mNegativeButtonText != null) {
				iviewConcelButton.setText(mNegativeButtonText);
				if (mNegativeButtonListener != null) {
					iviewConcelButton
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									mNegativeButtonListener.onClick(
											mIviewDialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				iviewConcelButton.setText("取消");
			}
			// set the content message
			if (mMessage != null)
				iview_dialog_message.setText(mMessage);
			else
				iview_dialog_message.setText("請輸入相關信息");
			mIviewDialog.setContentView(iview_dialog_layout);
			return mIviewDialog;
		}
	}
}
