package com.qriz.app.feature.onboard.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qriz.app.core.designsystem.theme.Gray200
import com.qriz.app.core.designsystem.theme.QrizTheme
import com.qriz.app.core.designsystem.theme.White

@Composable
fun SurveyItemCard(
    content: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onChecked: (Boolean) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth().clickable { onChecked(isChecked.not()) },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                horizontal = 24.dp,
            ),
        ) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
            )
            Checkbox(
                checked = isChecked,
                colors = CheckboxDefaults.colors().copy(
                    uncheckedBoxColor = Gray200,
                    uncheckedBorderColor = Gray200,
                    uncheckedCheckmarkColor = White,
                ),
                onCheckedChange = { onChecked(isChecked.not()) }
            )
        }
    }
}

@Preview
@Composable
private fun ConceptCheckOptionCardPreview() {
    QrizTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SurveyItemCard(
                content = "Preview",
                isChecked = false,
                onChecked = {}
            )
            SurveyItemCard(
                content = "Preview",
                isChecked = true,
                onChecked = {}
            )
        }
    }
}
