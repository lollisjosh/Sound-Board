import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class AudioRecorder {
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    fun startRecording(context: Context) {
        val fileName = "recording_${System.currentTimeMillis()}.mp3"
        audioFile = File(context.cacheDir, fileName)

        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(audioFile?.absolutePath)
            prepare()
            start()
        }
    }

    fun stopRecording(): File? {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        return audioFile
    }
}