package com.modura.app.ui.screens.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.modura.app.LocalRootNavigator
import com.modura.app.ui.theme.Gray500
import modura.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

object MypageSettingScreen : Screen {
    override val key: String = "MypageSettingScreenKey"

    @Composable
    override fun Content() {
        val rootNavigator = LocalRootNavigator.current
        var version = "1.0.0"
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = "뒤로가기",
                    modifier = Modifier.size(24.dp)
                        .clickable {
                            rootNavigator?.pop()
                        }
                )
                Spacer(Modifier.weight(1f))
            }
            Text("SETTING", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 20.dp))
            Spacer(Modifier.height(32.dp))
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Text("로그아웃", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.clickable {
                    println("로그아웃 클릭됨")
                })
                Text("계정탈퇴", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.clickable {
                    println("계정탈퇴 클릭됨")
                })
                Row{
                    Text("버전 정보", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.width(12.dp))
                    Text("version ${version}", style = MaterialTheme.typography.bodySmall, color = Gray500)
                }
                Column{
                    Text("약관 동의", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("플리커 이용약관 >", style = MaterialTheme.typography.bodySmall, color = Gray500)
                        Text("개인정보 처리방침 >", style = MaterialTheme.typography.bodySmall, color = Gray500)
                    }
                }
            }
        }
    }
}