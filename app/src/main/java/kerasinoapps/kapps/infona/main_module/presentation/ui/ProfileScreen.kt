package kerasinoapps.kapps.infona.main_module.presentation.ui


import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.common.birthdayToAge
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.profile.ProfileViewModel
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.weiss
import java.net.URI
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager




@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    userId:String?
) {

    val user = viewModel.userToShow.value
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        viewModel.uploadImage(uri, context)
    }
    val launcher2 = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launcher.launch("image/*")
        } else {
            // Permission Denied: Do something
            //Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }
    LaunchedEffect(key1 = true){
        viewModel.getUser(userId ?: "0")
        viewModel.getValues()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp, top = 25.dp, bottom = 20.dp)
    ) {
        val(name,email, ranking, infoColumn, pictureCircle, newPicture, separationLine, doneList) = createRefs()
        Triangle(
            topColor = dunkelgrau,
            bottomColor = blau,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = user.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = weiss,
            modifier = Modifier
                .constrainAs(name){
                    top.linkTo(parent.top, margin = 25.dp)
                }
        )
        Text(
            text = user.email,
            fontSize = 11.sp,
            color = weiss,
            modifier = Modifier
                .constrainAs(email){
                    top.linkTo(name.bottom, margin = 3.dp)
                }
        )
        Text(
            text = viewModel.ranking.value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = weiss,
            modifier = Modifier
                .constrainAs(ranking){
                    top.linkTo(email.bottom,margin = 40.dp)
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .constrainAs(infoColumn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            InfoPart(
                description = stringResource(id = R.string.university_profile),
                info = user.university
            )
            InfoPart(
                description = stringResource(id = R.string.alter_profile),
                info = birthdayToAge(user.birthday)
            )
            InfoPart(
                description = stringResource(id = R.string.averagegrade_profile),
                info = "${user.averageGrade}"
            )
        }
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(1.dp, color = dunkelgrau, shape = CircleShape)
                .background(weiss)
                .clickable {

                }
                .constrainAs(pictureCircle) {
                    end.linkTo(parent.end, margin = 10.dp)
                    top.linkTo(parent.top, margin = 10.dp)
                },
            contentAlignment = Alignment.Center
        ){
            if(viewModel.imageLoading.value||viewModel.uploadImageState.value.loading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(25.dp),
                    color = blau
                )
            }else if(viewModel.imageError.value!=null){
                //Errorimage
            }else if(viewModel.image.value!=null){
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    bitmap = viewModel.image.value!!,
                    contentDescription = "profile_picture"
                )
            }

        }
        if(user.isAppUser){
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(blau)
                    .size(30.dp)
                    .clickable {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) -> {
                                launcher.launch("image/*")
                            }
                            else -> {
                                launcher2.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                    .constrainAs(newPicture) {
                        end.linkTo(pictureCircle.end, margin = 5.dp)
                        bottom.linkTo(pictureCircle.bottom, margin = 5.dp)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "",
                    tint = weiss,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(separationLine) {
                    top.linkTo(infoColumn.bottom, margin = 40.dp)
                }

        ){
            drawLine(
                color = dunkelgrau,
                start = Offset(0f-150f,0f),
                end = Offset(size.width+150f,0f),
                strokeWidth = 2f
            )
        }
        DoneList(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(doneList) {
                    top.linkTo(separationLine.bottom, margin = 20.dp)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        )
    }


}

@Composable
fun Triangle(
    topColor:Color,
    bottomColor:Color,
    modifier:Modifier = Modifier
) {

    val colorList = listOf(bottomColor, topColor)
    Canvas(
        modifier = modifier
    ){
        rotate(70f){
            drawRoundRect(
                topLeft = Offset(size.width -2600f,-800f),
                size = size,
                brush = Brush.linearGradient(colorList,end = Offset(center.x-2000f,0f)),
                cornerRadius = CornerRadius(150f,150f)
            )
        }

    }

}

@Composable
fun InfoPart(
    description:String,
    info:String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ){
        Text(
            text = description,
            fontSize = 13.sp,
            color = dunkelgrau
        )
        Text(
            text = info,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = dunkelgrau
        )
    }
}

@Composable
fun DoneList(
    viewModel: ProfileViewModel,
    modifier:Modifier = Modifier
) {
    val profileState = viewModel.profileState.value
    val list = profileState.information
    if(profileState.showData){
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 15.dp)
        ){
            items(list.size) { index ->
                DoneListElement(
                    text = "${list[index].subject} (${list[index].doneExercises}/${list[index].exerciseCount})",
                    current = list[index].doneExercises,
                    max = list[index].exerciseCount
                )
            }
        }
    }

}

@Composable
fun DoneListElement(
    text:String,
    current:Int,
    max:Int
) {
    var part:Float = 0f
    if(max>0){
        part = (current*100/max)/100f
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ){
        Text(
            text = text,
            fontSize = 13.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .shadow(5.dp, RoundedCornerShape(25.dp))
                    .background(
                        color = weiss,
                        RoundedCornerShape(25.dp)
                    )
            ){

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(part)
                    .fillMaxHeight()
                    .background(
                        color = blau,
                        RoundedCornerShape(25.dp)
                    )
            ){

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrianglePreview() {
    InfonaTheme {
        Triangle(
            topColor = dunkelgrau,
            bottomColor = blau
        )
    }
}