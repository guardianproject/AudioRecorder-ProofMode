/*
 * Copyright 2020 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dimowner.audiorecorder.app.settings;

import com.dimowner.audiorecorder.Contract;

public class SettingsContract {

	interface View extends Contract.View {

		void showStoreInPublicDir(boolean b);

		void showKeepScreenOn(boolean b);
		void showRecordInStereo(boolean b);

		void showAskToRenameAfterRecordingStop(boolean b);

		void showRecordingBitrate(int bitrate);

		void showRecordingSampleRate(int rate);

		void showRecordingFormat(int format);

		void showNamingFormat(int format);

		void showAllRecordsDeleted();

		void showFailDeleteAllRecords();

		void showTotalRecordsDuration(String duration);
		void showRecordsCount(int count);
		void showAvailableSpace(String space);

		void showBitrateSelector();
		void hideBitrateSelector();

		void showDialogPublicDirInfo();

		void showDialogPrivateDirInfo();
	}

	public interface UserActionsListener extends Contract.UserActionsListener<SettingsContract.View> {

		void loadSettings();

		void storeInPublicDir(boolean b);

		void keepScreenOn(boolean b);

		void askToRenameAfterRecordingStop(boolean b);

		void recordInStereo(boolean stereo);

		void setRecordingBitrate(int bitrate);

		void setRecordingFormat(int format);

		void setNamingFormat(int format);

		void setSampleRate(int rate);

		void deleteAllRecords();
	}
}
