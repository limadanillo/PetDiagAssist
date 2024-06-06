import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS_USER: NbMenuItem[] = [

  {
    title: 'Dashboard',
    icon: 'home-outline',
    link: '/pages/dashboard',

  },
  {
    title: 'Usuários',
    icon: 'people-outline',
    link: '/pages/users',

  },
  {
    title: 'Exames',
    icon: 'layers-outline',
    link: '/pages/exams',
  },
  {
    title: 'Animais',
    icon: 'activity-outline',
    link: '/pages/animals',
  }
]

export const MENU_ITEMS_ADMIN: NbMenuItem[] = [
...MENU_ITEMS_USER,

  {
    title: 'Tools settings',
    link: '/admin/tools/settings',
    icon: 'file-outline',
    data: {
      permission: 'admin',
    }
  },
  {
    title: 'Other',
    group: true,
  },

  {
    title: 'Platform',
    icon: 'cube-outline',
    children: [
      {
        title: 'Settings',
        link: '/admin/platform/settings',
        icon: 'settings-2-outline',

      },
      {
        title: 'Statistics',
        link: '/admin/platform/stat',
        icon:'bar-chart-outline'
      }

    ],
  }
];
