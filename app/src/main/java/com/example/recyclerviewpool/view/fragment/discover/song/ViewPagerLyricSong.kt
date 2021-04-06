package com.example.recyclerviewpool.view.fragment.discover.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.recyclerviewpool.databinding.ViewpagerLyricSongBinding
import com.example.recyclerviewpool.view.MainActivity
import com.example.recyclerviewpool.lyricsong.LyricManager

class ViewPagerLyricSong : Fragment(), View.OnClickListener {


    private lateinit var lyricManager: LyricManager

    private lateinit var model: MainActivity
    private lateinit var binding: ViewpagerLyricSongBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = (activity as MainActivity)
        binding = ViewpagerLyricSongBinding.inflate(inflater, container, false)
        reg()
        lyricManager = LyricManager(context!!)


        return binding.root
    }

    private fun reg() {
        model.getModel().infoAlbum.observe(viewLifecycleOwner, Observer {
            if (it.lyricKaraoke == "") {
                binding.lyricA.setText(model.getModel().infoAlbum.value!!.lyricSong)
            } else {
                binding.lyricA.setText(model.getModel().infoAlbum.value!!.lyricKaraoke)

            }
            lyricManager.setLyricString("[00:00.00]Bài hát: Giàu Vì Bạn, Sang Vì Vợ - RPT MCK\n" +
                    "[00:13.71]Gucci bag, Louis V\n" +
                    "[00:14.90]Iced out, mà lại còn suy\n" +
                    "[00:16.52]Biến số 0 ở nhà băng\n" +
                    "[00:18.10]Tăng thêm nhiều hơn trong đêm nay\n" +
                    "[00:19.93]Stay away\n" +
                    "[00:20.74]Don't kill my vibe \n" +
                    "[00:23.86]Oh mama, I can not cry\n" +
                    "[00:26.85]Bạn thân ơi\n" +
                    "[00:28.57]Như bạn đã thấy \n" +
                    "[00:29.61]Tôi với bạn đều là dân chơi\n" +
                    "[00:31.87]Rồi mình lại hí hoáy với nhau \n" +
                    "[00:33.11]Biến căn phòng này \n" +
                    "[00:33.85]Trở thành phòng xông hơi\n" +
                    "[00:35.29]Rồi lại chạy cong đuôi\n" +
                    "[00:36.74]Làm mọi việc xong xuôi\n" +
                    "[00:38.69]Thật lòng luôn tôi mong \n" +
                    "[00:40.10]Kiếp sau vẫn là anh em\n" +
                    "[00:44.33]Và luôn luôn có những người \n" +
                    "[00:45.14]Anh em mà chỉ cần nhắc đến tên thôi\n" +
                    "[00:46.78]Đã đủ cho mình tinh thần \n" +
                    "[00:48.71]để biến bài thi hôm nay\n" +
                    "[00:48.82]Thành 1 cái liveshow rồi\n" +
                    "[00:53.79]Ey we got 16 typh\n" +
                    "[00:55.38]We are northside soldiers baby\n" +
                    "[00:57.95]Bước lên show\n" +
                    "[00:59.75]Anh em anh đi đông như quân Nguyên\n" +
                    "[01:01.49]Burn the stage\n" +
                    "[01:02.91]Xong anh em chơi banh như dân chuyên\n" +
                    "[01:04.85]Northside boys\n" +
                    "[01:06.41]Bọn anh đến đây để get the bag\n" +
                    "[01:08.13]Game so to\n" +
                    "[01:09.52]Bọn anh làm sập cả internet\n" +
                    "[01:11.46]Yeah honey\n" +
                    "[01:12.41]Thích quần áo đẹp bọn anh có Monkee\n" +
                    "[01:13.91]Bọn anh leo lên top 1 gà chọi\n" +
                    "[01:15.38]Như là bắn Gunny\n" +
                    "[01:17.21]Nói câu này thật chắc ăn khi\n" +
                    "[01:18.85]Tình anh em không bị cắt phăng đi\n" +
                    "[01:20.46]Mấy người ghen ghét\n" +
                    "[01:21.23]Watch our mouth\n" +
                    "[01:22.17]Ooh, ooh\n" +
                    "[01:23.11]Tắt văn đi\n" +
                    "[01:23.91]Bọn họ xếp thành hàng \n" +
                    "[01:25.52]Cho bọn anh đi lên\n" +
                    "[01:26.98]Và sẽ còn xa tít tìn tịt\n" +
                    "[01:28.71]Cho đến khi bọn anh ghi tên\n" +
                    "[01:30.18]Thích lên cả sóng truyền hình\n" +
                    "[01:31.83]Đóng tune như T-pain\n" +
                    "[01:33.49]Mỗi tối thứ 7 là biến show Rap Việt \n" +
                    "[01:35.13]Thành cái minigame\n" +
                    "[01:38.41]Bọn anh lướt trong city\n" +
                    "[01:39.38]Kiếm tiền tươi như G. T. A\n" +
                    "[01:41.08]Mic là cồn và họng là lửa\n" +
                    "[01:42.78]Xin giới thiệu MCK\n" +
                    "[01:44.56]Tuần thủ luật nên không nhập kho\n" +
                    "[01:46.08]Kiếm về cho anh em thật no\n" +
                    "[01:47.78]Fame thật to\n" +
                    "[01:48.56]Quen Tlinh\n" +
                    "[01:49.36]Em Thành Draw\n" +
                    "[01:50.47]Giàu vì bạn sang vì vợ\n" +
                    "[01:51.94]Và cả squad, không phải sợ\n" +
                    "[01:53.67]Keep it real keep it raw\n" +
                    "[01:55.17]Và phải nhớ 1 câu là\n" +
                    "[01:56.55]Giàu vì bạn sang vì vợ\n" +
                    "[01:58.52]Và cả squad, không phải sợ\n" +
                    "[02:00.14]Keep it real keep it trappin'\n" +
                    "[02:01.91]Và phải nhớ 1 câu là\n" +
                    "[02:03.42]Giàu vì bạn sang vì vợ\n" +
                    "[02:05.14]Và cả squad, không phải sợ\n" +
                    "[02:06.78]Keep it real keep it raw\n" +
                    "[02:08.37]Và phải nhớ 1 câu là\n" +
                    "[02:09.64]Giàu vì bạn sang vì vợ\n" +
                    "[02:11.64]Và cả squad, không phải sợ\n" +
                    "[02:13.34]Keep it real keep it trappin'\n" +
                    "[02:15.16]Và phải nhớ 1 câu là\n" +
                    "[02:18.14]Vợ anh quá chất\n" +
                    "[02:19.58]Biến anh thành bá nhất\n" +
                    "[02:21.01]Cờ đến tay là phất\n" +
                    "[02:22.37]It's lit\n" +
                    "[02:24.44]Ngựa non, háu đá\n" +
                    "[02:25.80]Đẹp giai, láu cá\n" +
                    "[02:27.41]Trút vào mic khi cáu quá\n" +
                    "[02:31.06]Không bao giờ được \n" +
                    "[02:31.50]Mất chất trên bàn tiệc\n" +
                    "[02:32.53]Việc cần làm bây giờ là làm việc\n" +
                    "[02:34.03]Cũng anh em đi lên thì càng tuyệt\n" +
                    "[02:35.54]Nhạc của bọn anh toàn là hàng tuyển\n" +
                    "[02:37.25]Công việc cứ càng ngày càng uyển\n" +
                    "[02:38.98]Hiphop không phải để làm kiểng\n" +
                    "[02:40.56]Để làm những thứ bọn anh thích\n" +
                    "[02:42.28]Tiền về cứ phải gọi là hàng quyển\n" +
                    "[02:44.23]Nhớ gọi anh trước vì \n" +
                    "[02:45.06]Anh không biết số em\n" +
                    "[02:45.92]Em là vệ tinh còn anh là hố đen\n" +
                    "[02:47.39]Anh cạo trọc chứ không phải cắt moi\n" +
                    "[02:48.90]Anh là bad boy chứ không phải ahihi boy\n" +
                    "[02:50.95]Anh xin số để em còn chốt đơn\n" +
                    "[02:52.36]Không tìm thấy người con trai nào tốt hơn\n" +
                    "[02:53.86]Anh là lửa nên em muốn đốt rơm\n" +
                    "[02:55.59]0326 anh có vợ rồi\n" +
                    "[02:57.31]Ra đường anh là cá mập\n" +
                    "[02:58.68]Ở nhà anh là cá con\n" +
                    "[03:00.56]Chúng nó bảo anh sợ vợ\n" +
                    "[03:02.05]Anh bảo chúng nó quá non\n" +
                    "[03:03.72]Nhà nào mà chả có mái\n" +
                    "[03:05.40]Không phải sợ đấy là tôn trọng\n" +
                    "[03:07.17]Không nói to hạ cái tone giọng\n" +
                    "[03:09.11]Đơn giản bởi vì\n" +
                    "[03:09.97]Giàu vì bạn sang vì vợ\n" +
                    "[03:11.46]Và cả squad, không phải sợ\n" +
                    "[03:12.98]Keep it real keep it raw\n" +
                    "[03:14.56]Và phải nhớ 1 câu là\n" +
                    "[03:15.98]Giàu vì bạn sang vì vợ\n" +
                    "[03:18.04]Và cả squad, không phải sợ\n" +
                    "[03:19.53]Keep it real keep it trappin'\n" +
                    "[03:21.37]Và phải nhớ 1 câu là\n" +
                    "[03:22.59]Giàu vì bạn sang vì vợ\n" +
                    "[03:24.53]Và cả squad, không phải sợ\n" +
                    "[03:26.08]Keep it real keep it raw\n" +
                    "[03:27.90]Và phải nhớ 1 câu là\n" +
                    "[03:29.13]Giàu vì bạn sang vì vợ\n" +
                    "[03:31.09]Và cả squad, không phải sợ\n" +
                    "[03:32.84]Keep it real keep it trappin'\n" +
                    "[03:34.66]Và phải nhớ 1 câu là\n" +
                    "[03:37.09]")


        })
    }


    override fun onClick(v: View?) {
    }

}