package kerasinoapps.kapps.infona.main_module.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Tipp
import kerasinoapps.kapps.infona.main_module.presentation.composables.exercise.TippCard
import kerasinoapps.kapps.infona.main_module.presentation.ui.exercise.ConfirmBuyTipDialog
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.exercise.ExerciseViewModel

@ExperimentalComposeUiApi
@Composable
fun TippDialog(
    viewModel: ExerciseViewModel,
    tipps:List<Tipp>,
    onDismiss:()->Unit,
    alreadyDone:Boolean
) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .padding(15.dp)
            ){
                for(i in tipps.indices){
                    TippCard(
                        tipp = tipps[i],
                        tippNumber = "Tipp ${i+1}",
                        alreadyBought = tipps[i].alreadyBought,
                        onClick = {
                            viewModel.onItemClickTipps(tipps[i].id)
                                  },
                        isExpaned = viewModel.revealedTipIds.value.contains(tipps[i].id),
                        onClickToBuy = {
                            if(!alreadyDone){
                                viewModel.onSelectedTipToBuyChange(tipps[i])
                                viewModel.onShowConfirmBuyTipDialog()
                            }
                        }
                    )
                }
            }
        }

    }
    if(viewModel.showConfirmBuyTipDialog.value){
        ConfirmBuyTipDialog(
            onDismiss = {
                viewModel.onDismissConfirmBuyTipDialog()
            },
            onSubmit = {
                viewModel.buyTip(viewModel.selectedTipToBuy.value!!)
                viewModel.onDismissConfirmBuyTipDialog()
            },
            tipp = viewModel.selectedTipToBuy.value!!
        )
    }

}