package me.izzp.jetsurveydemo.form

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.material.datepicker.MaterialDatePicker
import me.izzp.jetsurveydemo.*
import me.izzp.jetsurveydemo.R
import me.izzp.jetsurveydemo.ui.theme.grey
import me.izzp.jetsurveydemo.ui.theme.mtColors
import me.izzp.jetsurveydemo.ui.theme.mtShapes
import me.izzp.jetsurveydemo.ui.theme.mtTypography
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun QuestionBox(
    vm: FormViewModel,
    question: Question,
    value: Any?,
    callback: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text(
            text = question.question,
            style = mtTypography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .background(mtColors.grey, mtShapes.medium)
                .padding(8.dp, 24.dp),
        )
        Spacer(Modifier.height(20.dp))
        if (question.type == QuestionType.single || question.type == QuestionType.multi) {
            SimpleQuestionContent(
                question = question,
                value = value as List<Any>?,
                callback = callback,
            )
        } else if (question.type == QuestionType.date) {
            DateQuestionContent(
                question = question,
                value = value as Date?,
                callback = callback,
            )
        } else if (question.type == QuestionType.progress) {
            ProgressQuestionContent(
                question = question,
                value = value as Float?,
                callback = callback,
            )
        } else if (question.type == QuestionType.photo) {
            PhotoQuestionContent(
                vm = vm,
                question = question,
                image = value as Uri?,
                callback = callback,
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PhotoQuestionContent(
    vm: FormViewModel,
    question: Question,
    image: Uri?,
    callback: (Uri) -> Unit,
) {
    println("PhotoQuestionContent: $image")
    println("needPermission: ${vm.imageStore.needPermission}")
    if (vm.imageStore.needPermission) {
        val permission = rememberPermissionState(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        PermissionRequired(
            permissionState = permission,
            permissionNotGrantedContent = {
                PhotoContainer(
                    onClick = {
                        permission.launchPermissionRequest()
                    },
                    content = {
                        Text(buildString {
                            appendLine("hasPermission: ${permission.hasPermission}")
                            appendLine("shouldShowRationale: ${permission.shouldShowRationale}")
                            append("requested: ${permission.permissionRequested}")
                        })
                    },
                )
            },
            permissionNotAvailableContent = {
                PhotoContainer(
                    onClick = null,
                    content = {
                        Text("permissionNotAvailable")
                    },
                )
            },
            content = {
                val uri = vm.imageStore.uri
                if (uri == null) {
                    PhotoContainer(null) {
                        Text("Uri is null")
                    }
                } else {
                    val launcher =
                        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
                            println("拍照结果: $it")
                            if (it) {
                                callback(uri)
                            }
                        }
                    PhotoContainer(
                        onClick = { launcher.launch(uri) },
                        content = { PhotoContent(image) }
                    )
                }
            }
        )
    } else {
        val uri = vm.imageStore.uri
        if (uri == null) {
            PhotoContainer(null) {
                Text("Uri is null")
            }
        } else {
            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
                    println("拍照结果: $it")
                    if (it) {
                        callback(uri)
                    }
                }
            PhotoContainer(
                onClick = { launcher.launch(uri) },
                content = { PhotoContent(image) }
            )
        }
    }
}

@Composable
private fun PhotoContainer(
    onClick: (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, mtColors.grey, mtShapes.large)
            .clip(mtShapes.large)
            .then(
                if (onClick == null) {
                    Modifier
                } else {
                    Modifier.clickable(onClick = onClick)
                }
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content,
    )
}

@Composable
private fun PhotoContent(image: Uri?) {
    if (image != null) {
        println("imageRequest")
        Image(
            rememberImagePainter(image),
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3 / 2f)
                .clip(mtShapes.medium)
        )
    } else {
        Image(
            painterResource(
                if (isSystemInDarkTheme()) R.drawable.ic_selfie_dark else R.drawable.ic_selfie_light,
            ),
            null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.padding(18.dp).fillMaxWidth(0.7f),
        )
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(0.dp, 12.dp)
    ) {
        Icon(
            painterResource(R.drawable.ic_baseline_add_a_photo_24),
            null,
            tint = mtColors.primary
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = "ADD PHOTO",
            style = mtTypography.subtitle1,
            color = mtColors.primary,
        )
    }
}


@Composable
private fun ProgressQuestionContent(
    question: Question,
    value: Float?,
    callback: (Float) -> Unit,
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Slider(
            value = value ?: 0f,
            onValueChange = {
                callback(it)
            },
            steps = question.progressValues!!.steps,
            modifier = Modifier.padding(10.dp)
        )
        Box(
            Modifier.fillMaxWidth()
        ) {
            Text(
                text = question.progressValues.titles[0],
                style = mtTypography.caption,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = question.progressValues.titles[1],
                style = mtTypography.caption,
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                text = question.progressValues.titles[2],
                style = mtTypography.caption,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }

}

@Composable
private fun DateQuestionContent(
    question: Question,
    value: Date?,
    callback: (Date) -> Unit
) {
    val context = LocalContext.current
    val sdf = remember { SimpleDateFormat("yyyy-MM-dd") }
    OutlinedButton(
        onClick = {
            val datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setSelection(value?.time ?: System.currentTimeMillis())
                .build()
            datePicker.addOnPositiveButtonClickListener {
                callback(Date(it))
            }
            datePicker.isCancelable = false
            val fragmentManager = (context as FragmentActivity).supportFragmentManager
            datePicker.show(fragmentManager, "datepicker")
        },
        modifier = Modifier.fillMaxWidth().height(64.dp)
    ) {
        Text(sdf.format(value ?: Date()))
        Spacer(Modifier.weight(1f))
        Icon(
            Icons.Default.ArrowDropDown,
            null,
        )
    }
}

@Composable
private fun SimpleQuestionContent(
    question: Question,
    value: List<Any>?,
    callback: (List<Any>) -> Unit,
) {
    question.options!!.forEach { option ->
        val v = (if (option.image > 0) option.image else option.title!!)
        val selected = value?.contains(v) ?: false
        Spacer(Modifier.height(12.dp))
        OptionItem(
            question = question,
            option = option,
            selected = selected,
            onClick = {
                if (question.type == QuestionType.single) {
                    callback(listOf(v))
                } else {
                    val rtn = value?.toMutableList() ?: mutableListOf()
                    rtn.addOrRemove(v)
                    callback(rtn)
                }
            }
        )
    }
}

@Composable
private fun OptionItem(
    question: Question,
    option: Option,
    selected: Boolean,
    onClick: () -> Unit,
) {
    var mod = Modifier.fillMaxWidth().height(64.dp)
    if (selected) {
        mod = mod.background(mtColors.primary.copy(0.3f), mtShapes.medium)
    }
    mod = mod.border(
        width = 1.dp,
        color = if (selected) mtColors.primary else mtColors.grey,
        shape = mtShapes.medium,
    )
        .clip(mtShapes.medium)
        .clickable(onClick = onClick)
        .padding(12.dp, 0.dp)
    Box(
        modifier = mod
    ) {
        if (option.image > 0) {
            Image(
                painterResource(option.image),
                null,
                modifier = Modifier.size(48.dp).clip(mtShapes.small).align(Alignment.CenterStart),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = option.title ?: "",
                style = mtTypography.body1,
                modifier = Modifier.align(Alignment.Center),
            )
        } else if (option.title != null) {
            Text(
                text = option.title,
                style = mtTypography.body1,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        if (question.type == QuestionType.single) {
            RadioButton(
                selected = selected,
                onClick = null,
                modifier = Modifier.align(Alignment.CenterEnd),
                colors = RadioButtonDefaults.colors(mtColors.primary)
            )
        } else {
            Checkbox(
                checked = selected,
                onCheckedChange = null,
                modifier = Modifier.align(Alignment.CenterEnd),
                colors = CheckboxDefaults.colors(mtColors.primary)
            )
        }
    }
}

@Composable
@Preview
private fun PreviewQuestionItem() {
    var selected by rememberBoolean()
    val question = questionsData.first { it.type == QuestionType.single }
    OptionItem(
        question = question,
        option = question.options!!.first(),
        selected = selected,
        onClick = {
            selected = !selected
        }
    )
}